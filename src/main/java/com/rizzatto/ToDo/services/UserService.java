package com.rizzatto.ToDo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rizzatto.ToDo.dto.UserDtoRequest;
import com.rizzatto.ToDo.entity.User;
import com.rizzatto.ToDo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder passEncoder;
	
	public User save(UserDtoRequest userRequest) throws Exception{
		if(repository.findByEmail(userRequest.getEmail()).isEmpty()){
			User user = UserDtoRequest.createUser(userRequest);
			user.setPassword(passEncoder.encode(user.getPassword()));
			return repository.save(user);
		}
		throw new Exception("This email already exist");
	}
	
	public List<User> getUsers(){
		return repository.findAllWithTasks();
	}
	
	public Optional<User> getById(Long id) {
		return repository.findById(id);
	}
	
	public User login(String email, String password) throws Exception{
		Optional<User> obj = repository.findByEmail(email);
		if(obj.isEmpty()) {
			throw new Exception("Dont exists any account with this email");
		}
		User user = obj.get();
		
		if(!passEncoder.matches(password, user.getPassword())) {
			throw new Exception("Wrong password");
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
		} else if(!passEncoder.matches(userToUpdate.getPassword(), user.getPassword())) {
			user.setPassword(passEncoder.encode(userToUpdate.getPassword()));
		}
		
		return repository.save(user);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	

}
