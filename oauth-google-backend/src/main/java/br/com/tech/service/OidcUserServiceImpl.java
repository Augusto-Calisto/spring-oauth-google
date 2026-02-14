package br.com.tech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class OidcUserServiceImpl implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private static final Logger logger = LoggerFactory.getLogger(OidcUserServiceImpl.class);

	private OidcUserService oidcUserService;
	
	@Autowired
	public OidcUserServiceImpl(OidcUserService oidcUserService) {
		this.oidcUserService = oidcUserService;
	}
	
	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		
		logger.info("Registration ID: {}", registrationId);
		
		OidcUser oidcUser = oidcUserService.loadUser(userRequest);
		
		String name = oidcUser.getAttribute("name");
		
		String email = oidcUser.getAttribute("email");
		
		logger.info("Nome: {}, Email: {}", name, email);

		return oidcUser;
	}
}