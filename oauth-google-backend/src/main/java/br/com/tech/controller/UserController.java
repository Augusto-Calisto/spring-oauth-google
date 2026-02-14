package br.com.tech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech.dto.AuthSuccessResponse;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@GetMapping(value = "/info")
	public ResponseEntity<AuthSuccessResponse> getUserInfo(@AuthenticationPrincipal OidcUser auth) {				
		if(auth == null) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.build();
		}
				
		AuthSuccessResponse authSuccess = AuthSuccessResponse
										.builder()
											.name(auth.getClaim("name"))
											.email(auth.getClaim("email"))
											.familyName(auth.getClaim("family_name"))
											.urlPicture(auth.getClaim("picture"))
											.token(auth.getIdToken().getTokenValue())
											.role("USER")
										.build();
					
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(authSuccess);
	}
}