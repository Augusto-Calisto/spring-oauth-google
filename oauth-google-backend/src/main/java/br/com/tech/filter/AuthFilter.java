package br.com.tech.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
			response.setHeader("Cache-Control", "no-cache, no-store, must revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
		}
		
		filterChain.doFilter(request, response);
	}
}