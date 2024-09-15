package com.rizzatto.ToDo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rizzatto.ToDo.dto.UserDtoRequest;
import com.rizzatto.ToDo.entity.User;
import com.rizzatto.ToDo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public User save(UserDtoRequest userRequest){
		if(repository.findByEmail(userRequest.getEmail()).isEmpty()){
			User user = UserDtoRequest.createUser(userRequest);
			return repository.save(user);
		}
		throw new RuntimeException("This email already exist");
	}
	
	public List<User> getUsers(){
		return repository.findAllWithTasks();
	}
	
	public Optional<User> getById(Long id) {
		return repository.findById(id);
	}
	
	public User login(String email, String password){
		Optional<User> obj = repository.findByEmail(email);
		if(obj.isEmpty()) {
			throw new RuntimeException("Dont exists any account with this email");
		}
		User user = obj.get();
		
		if(!user.getPassword().equals(password)) {
			throw new RuntimeException("Wrong password");
		}
		
		return user;
	}
	
	public Optional<User> getByEmail(String email){
		return repository.findByEmail(email);
	}
	
	public User update(Long id, UserDtoRequest userToUpdate) {
		User user = repository.getReferenceById(id);
		
		if(!user.getName().equals(userToUpdate.getName())) {
			user.setName(userToUpdate.getName());
		} else if(!user.getPassword().equals(userToUpdate.getPassword())) {
			user.setPassword(userToUpdate.getPassword());
		}
		
		return repository.save(user);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	

}
