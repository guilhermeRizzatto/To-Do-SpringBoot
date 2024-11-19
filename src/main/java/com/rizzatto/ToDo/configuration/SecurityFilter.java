package com.rizzatto.ToDo.configuration;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rizzatto.ToDo.entity.User;
import com.rizzatto.ToDo.repository.UserRepository;
import com.rizzatto.ToDo.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
		String token = this.recoverToken(request);
		
		String login = tokenService.validateToken(token);
		
		if(login != null) {
			User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("Usuario nao encontrado com esse token blalalalalla"));
			var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
			var authentication = new UsernamePasswordAuthenticationToken(user, null,authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);;
		}
		filterChain.doFilter(request, response);
	}
	
	private String recoverToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("token".equals(cookie.getName())) {
					System.out.println(cookie.getValue());
					token = cookie.getValue();
					return token.replace("token=", "");		
				}
			}
		}
		return null;
	}

}
