package br.com.tech.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import br.com.tech.entity.Role;
import br.com.tech.entity.User;

@Service
public class OidcUserServiceImpl implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private static final Logger logger = LoggerFactory.getLogger(OidcUserServiceImpl.class);

	private OidcUserService oidcUserService;
	private UserService userService;
	
	@Autowired
	public OidcUserServiceImpl(OidcUserService oidcUserService, UserService userService) {
		this.oidcUserService = oidcUserService;
		this.userService = userService;
	}
	
	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = oidcUserService.loadUser(userRequest);
		
		
		String sub = oidcUser.getAttribute("sub");
		
		String name = oidcUser.getAttribute("name");
		
		String email = oidcUser.getAttribute("email");
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();		

		
		logger.info("Sub: {}, Registration ID: {}", sub, registrationId);

		
		Optional<User> optional = userService.findBySubAndRegistrationId(sub, registrationId);
		
		User user = optional.get();
		
		if(optional.isEmpty()) {
			Role userRole = Role.builder()
									.name("USER")
								.build();
			
			User newUser = User.builder()
									.name(name)
									.sub(sub)
									.registrationId(registrationId)
									.email(email)
									.roles(List.of(userRole))
								.build();
			
			user = userService.saveUser(newUser);
		}
		
		
		logger.info("Name: {}, Email: {}", name, email);
				
		
		DefaultOidcUser defaultOidcUser = new DefaultOidcUser(user.getRoles(), oidcUser.getIdToken(), oidcUser.getUserInfo());

		return defaultOidcUser;
	}
}