package com.rizzatto.ToDo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rizzatto.ToDo.dto.UserDtoRequest;
import com.rizzatto.ToDo.dto.UserDtoResponse;
import com.rizzatto.ToDo.entity.User;
import com.rizzatto.ToDo.services.TokenService;
import com.rizzatto.ToDo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class loginController {

	@Autowired
	private UserService service;
	
	@Autowired
	private CookieController cookieController;
	
	@Autowired
	private TokenService tokenService;
	

	@PostMapping("/create")
	public ResponseEntity<?> saveUser(@RequestBody UserDtoRequest request) {
		try {
			User user = service.save(request);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.toString());
		}
	}

	@GetMapping("/enter")
	public ResponseEntity<?> get(@RequestParam String email, @RequestParam String password, HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = null;
			
			user = service.login(email, password);
			
			String refreshToken = tokenService.generateRefreshToken(user.getEmail());
			
			String token = this.tokenService.generateToken(user.getEmail());
			cookieController.sendCookies(response, token, refreshToken);
			return ResponseEntity.status(HttpStatus.OK).body(new UserDtoResponse(user));
				
		} catch (Exception e) {
			return ResponseEntity.status(404).body(e.toString());
		}
	}
	
	
	
	

}
