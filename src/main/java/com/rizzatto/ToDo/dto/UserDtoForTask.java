package com.rizzatto.ToDo.dto;

import com.rizzatto.ToDo.entity.User;

public class UserDtoForTask {

	private String name;
	private String email;

	public UserDtoForTask(User user) {
		super();
		this.name = user.getName();
		this.email = user.getEmail();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
