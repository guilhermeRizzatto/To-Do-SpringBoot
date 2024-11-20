package com.rizzatto.ToDo.configuration;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rizzatto.ToDo.controller.CookieController;
import com.rizzatto.ToDo.entity.User;
import com.rizzatto.ToDo.repository.UserRepository;
import com.rizzatto.ToDo.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	 CookieController cookieController;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
		String token = this.cookieController.recoverToken(request);
		
		String login = tokenService.validateToken(token);
		
		if(login != null) {
			User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User not found with this token"));
			var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
			var authentication = new UsernamePasswordAuthenticationToken(user, null,authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);;
		}
		filterChain.doFilter(request, response);
	}
	

}
