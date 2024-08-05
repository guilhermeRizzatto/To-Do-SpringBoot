package com.rizzatto.ToDo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rizzatto.ToDo.dto.TaskDtoRequest;
import com.rizzatto.ToDo.entity.Task;
import com.rizzatto.ToDo.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository repository;
	
	public Task save(TaskDtoRequest taskRequest) {
		Task task = TaskDtoRequest.createTask(taskRequest);
		return repository.save(task);
	}
	
	public List<Task> getByUserId(Long id) {
		return repository.findByUserId(id);
	}
	
	public Task update(Long id, TaskDtoRequest taskRequest) {
		Task task = repository.getReferenceById(id);
		
		Task taskToUpdate = TaskDtoRequest.createTask(taskRequest);
		
		if(!task.getName().equals(taskToUpdate.getName())) {
			task.setName(taskToUpdate.getName());
		} else if(!task.getPriority().equals(taskToUpdate.getPriority())) {
			task.setPriority(taskToUpdate.getPriority());
			task.setPoints(task.getPriority());
		}
		
		task.setDescription(taskToUpdate.getDescription());
	
		return repository.save(task);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	

}
