package br.com.tech.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech.dto.AuthSuccessResponse;
import br.com.tech.entity.Role;
import br.com.tech.entity.User;
import br.com.tech.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/info")
	public ResponseEntity<AuthSuccessResponse> getUserInfo(@AuthenticationPrincipal OidcUser oidcUser, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {				
		if(oidcUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		String sub = oidcUser.getAttribute("sub");
		
		String registrationId = authorizedClient.getClientRegistration().getRegistrationId();
		
		
		Optional<User> optional = userService.findBySubAndRegistrationId(sub, registrationId);
		
		if(optional.isPresent()) {
			User user = optional.get();
			
			List<String> userRoles = user.getRoles().stream()
													.map(Role::getName)
													.collect(Collectors.toList());
			
			AuthSuccessResponse authSuccess = AuthSuccessResponse
												.builder()
													.name(user.getName())
													.email(user.getEmail())
													.familyName(oidcUser.getClaim("family_name"))
													.urlPicture(oidcUser.getClaim("picture"))
													.token(oidcUser.getIdToken().getTokenValue())
													.roles(userRoles)
												.build();
						
			return ResponseEntity.status(HttpStatus.OK).body(authSuccess);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}