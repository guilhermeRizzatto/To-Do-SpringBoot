package com.rizzatto.ToDo.dto;

import java.util.ArrayList;
import java.util.List;

import com.rizzatto.ToDo.entity.Task;
import com.rizzatto.ToDo.enums.Priority;

public class TaskDtoResponse {

	private Long id;
	private String name;
	private String description;
	private Boolean done;
	private Priority priority;
	private Integer points;

	public TaskDtoResponse(Task task) {
		super();
		this.id = task.getId();
		this.name = task.getName();
		this.description = task.getDescription();
		this.done = task.getDone();
		this.priority = task.getPriority();
		this.points = task.getPoints();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	
	public static List<TaskDtoResponse> createTasksDto(List<Task> tasks) {
		List<TaskDtoResponse> tasksDto = new ArrayList<TaskDtoResponse>();
		for(Task x : tasks) {
			TaskDtoResponse dto = new TaskDtoResponse(x);
			tasksDto.add(dto);
		}
		return tasksDto;
	}

	

}
