package br.com.tech.config;

import br.com.tech.service.OidcUserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
		
	private OidcUserServiceImpl oidcUserService;
	
	@Autowired
	public SecurityConfiguration(OidcUserServiceImpl oidcUserService) {
		this.oidcUserService = oidcUserService;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.cors((cors) -> {
					cors.configurationSource((req) -> {
						CorsConfiguration cfg = new CorsConfiguration();
						cfg.addAllowedOrigin("http://localhost:5173");
						cfg.setAllowCredentials(true);
						return cfg;
					});
				})
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((authorizeConfig) -> {
					authorizeConfig.requestMatchers("/login").permitAll();
					authorizeConfig.requestMatchers("/logout").permitAll();
					authorizeConfig.anyRequest().authenticated();
				})
				.oauth2Login((oauth2) -> {
					oauth2.userInfoEndpoint((userInfo) -> userInfo.oidcUserService(oidcUserService));
					oauth2.defaultSuccessUrl("http://localhost:5173", true);
					oauth2.failureUrl("/login?error=true");
				})
				.logout((logout) -> {
					logout.deleteCookies("JSESSIONID");
					logout.clearAuthentication(true);
					logout.logoutSuccessUrl("http://localhost:5173/login");
					logout.permitAll();
				})
				.exceptionHandling((ex) -> {
					ex.authenticationEntryPoint((req, res, e) -> {
						res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					});

					ex.accessDeniedHandler((req, res, e) -> {
						res.sendError(HttpServletResponse.SC_FORBIDDEN);
					});
				})
				.build();
	}
}