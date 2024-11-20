package com.rizzatto.ToDo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private CookieController cookieController;

	@Autowired
	private TokenService tokenService;

	@GetMapping("/get/all")
	public ResponseEntity<List<UserDtoResponse>> get() {
		List<User> users = service.getUsers();
		List<UserDtoResponse> response = UserDtoResponse.createUsersDto(users);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/get/one")
	public ResponseEntity<UserDtoResponse> get(@RequestParam String email, HttpServletRequest request, HttpServletResponse response) {
		Optional<User> user = null;

		String token = cookieController.recoverToken(request);
		
		if(email == null || email.equals("") || email.isEmpty()) {
			email = tokenService.validateToken(token);			
		}

		user = service.getByEmail(email);

		if (user.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
	

		token = this.tokenService.generateToken(user.get().getEmail());

		cookieController.sendCookies(response, token, null);
		return ResponseEntity.ok(new UserDtoResponse(user.get()));
	}

	@PutMapping("/put")
	public ResponseEntity<UserDtoResponse> update(@RequestBody UserDtoRequest objRequest, @RequestParam Long id, HttpServletResponse response) {
		User user = service.update(id, objRequest);

		UserDtoResponse objResponse = new UserDtoResponse(user);
		return ResponseEntity.ok(objResponse);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<User> delete(@RequestParam Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
