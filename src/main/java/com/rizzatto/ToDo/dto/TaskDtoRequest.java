package com.rizzatto.ToDo.dto;

import com.rizzatto.ToDo.entity.Task;
import com.rizzatto.ToDo.entity.User;
import com.rizzatto.ToDo.enums.Priority;

public class TaskDtoRequest {
	
	
    private String name;
    private String description;
    private Priority priority;
    private Boolean done;
    private User user;
    
	public TaskDtoRequest(String name, String description, Priority priority, User user, Boolean done) {
		super();
		this.name = name;
		this.description = description;
		this.priority = Priority.HIGH;
		this.done = done;
		this.user = new User();
	}
	
	public static Task createTask(TaskDtoRequest request) {
		Task task = new Task(Long.valueOf(0), request.getName(), request.getDescription(), Priority.HIGH, request.getUser(), request.getDone());
		
		return task;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}
	
	
    
    

}
