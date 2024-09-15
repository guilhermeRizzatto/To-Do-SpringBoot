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

@RestController
@RequestMapping("/login")
public class loginController {

	@Autowired
	private UserService service;

	@PostMapping("/create")
	public ResponseEntity<User> saveUser(@RequestBody UserDtoRequest request) {
		User user = service.save(request);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/enter")
	public ResponseEntity<?> get(@RequestParam(required = false) String email, @RequestParam(required = false) String password) {
		try {
			User user = null;
			if (email != null && password != null) {
				user = service.login(email, password);
			}
			return ResponseEntity.ok(new UserDtoResponse(user));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(e.toString());
		}
	}

}
