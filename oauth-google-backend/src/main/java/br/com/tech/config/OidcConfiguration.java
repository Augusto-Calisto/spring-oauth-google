package br.com.tech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

@Configuration
@Order(value = 1)
public class OidcConfiguration {

	@Bean
	OidcUserService oidcUserService() {
	    return new OidcUserService();   // Serviço padrão para carregar dados do usuário via OIDC
	}
}
