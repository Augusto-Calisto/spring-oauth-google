package br.com.tech.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtDecoderConfiguration {
	
	@Value("${spring.security.oauth2.client.provider.google.issuer-uri}")
	private String uriIdentityProvider;
	
	@Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withIssuerLocation(uriIdentityProvider).build();
        return jwtDecoder;
    }
}
