package com.rizzatto.ToDo.dto;

import java.util.ArrayList;
import java.util.List;

import com.rizzatto.ToDo.entity.User;

public class UserDtoResponse {

	private Long id;
	private String name;
	private String email;
	private List<TaskDtoForUser> tasks;


	public UserDtoResponse(User user) {
			super();
			this.id = user.getId();
			this.name = user.getName();
			this.email = user.getEmail();
			this.tasks = TaskDtoForUser.createTasksDto(user.getTasks());
		}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	public List<TaskDtoForUser> getTasks() {
		return tasks;
	}


	public void setTasks(List<TaskDtoForUser> tasks) {
		this.tasks = tasks;
	}


	public static List<UserDtoResponse> createUsersDto(List<User> users) {
		List<UserDtoResponse> usersDto = new ArrayList<UserDtoResponse>();
		for(User x : users) {
			UserDtoResponse dto = new UserDtoResponse(x);
			usersDto.add(dto);
		}
		return usersDto;
	}

}
