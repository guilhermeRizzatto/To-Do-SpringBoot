package com.rizzatto.ToDo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rizzatto.ToDo.dto.TaskDtoRequest;
import com.rizzatto.ToDo.dto.TaskDtoResponse;
import com.rizzatto.ToDo.entity.Task;
import com.rizzatto.ToDo.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService service;

	@PostMapping("/post")
	public ResponseEntity<TaskDtoResponse> saveTask(@RequestBody TaskDtoRequest request) {
		Task task = service.save(request);
		TaskDtoResponse response = new TaskDtoResponse(task);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/get/all")
	public ResponseEntity<List<TaskDtoResponse>> get(@RequestParam Long userId){
		List<Task> tasks = service.getByUserId(userId);
		List<TaskDtoResponse> response = TaskDtoResponse.createTasksDto(tasks);
		return ResponseEntity.ok(response);
	}
}
