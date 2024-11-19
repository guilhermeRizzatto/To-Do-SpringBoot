package com.rizzatto.ToDo.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rizzatto.ToDo.dto.UserDtoResponse;
import com.rizzatto.ToDo.entity.User;
import com.rizzatto.ToDo.repository.UserRepository;
import com.rizzatto.ToDo.services.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private cookieController cookieController;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/refresh")
	public ResponseEntity<?> getRefreshToken(HttpServletRequest request, HttpServletResponse response) {
		
		String refreshToken = cookieController.getRefreshTokenCookie(request);
		
		String email = tokenService.validateRefreshToken(refreshToken);
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario nao encontrado com esse token"));
		var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
		var authentication = new UsernamePasswordAuthenticationToken(user, null,authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);;
		
		String token = tokenService.generateToken(email);
		refreshToken = tokenService.generateRefreshToken(email);
		
		cookieController.sendCookies(response, null, token, refreshToken);
		
		return ResponseEntity.status(HttpStatus.OK).body(new UserDtoResponse(user));
			
	}

}
