package com.rizzatto.ToDo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.rizzatto.ToDo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class loginController {

	@Autowired
	private UserService service;
	
	@Autowired
	private cookieController cookieController;

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
	public ResponseEntity<?> get(@RequestParam(required = false) String email, @RequestParam(required = false) String password, HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = null;
			
			String[] emailAndPassword = cookieController.getLoginCookies(request);
			
			if(emailAndPassword != null) {
				if((emailAndPassword[0].equals(email) && emailAndPassword[1].equals(password)) || (email.equals("") || password.equals(""))) {
					email = emailAndPassword[0];
					password = emailAndPassword[1];	
				}
			}
			
			
			user = service.login(email, password);
			
			cookieController.sendLoginCookies(response, user);
			
			return ResponseEntity.ok(new UserDtoResponse(user));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(e.toString());
		}
	}
	
	
	
	

}
