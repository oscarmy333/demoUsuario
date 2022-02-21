package com.myoscorp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myoscorp.exception.UserNotFoundException;
import com.myoscorp.model.User;
import com.myoscorp.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping(path = {"greetings","hello","hola"})
	public String greetings() {
		return "Hola mundo!";
	}
	
	@PostMapping(path = "user")
	public ResponseEntity<Void> addUser(@RequestBody User user){
		
		try {
			User createdUser=userRepository.save(user);
			HttpHeaders headers = new HttpHeaders();
			headers.add("id", createdUser.getUserId().toString());
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}		
	}
	
	@GetMapping(path = "user/{id}")
	public ResponseEntity<User> get(@PathVariable("id") String id ) throws UserNotFoundException{
		try {
			Optional<User> user = userRepository.findById(Long.valueOf(id));
			return new ResponseEntity<User>(user.get(),HttpStatus.OK);
		} catch (Exception e) {
			throw new UserNotFoundException("Usuario no encontrado para el id "+id);
		}
	}
	
	@PutMapping(path = "user")
	public ResponseEntity<User> update(@RequestBody User user) {
		try {
			User updatedUser = userRepository.save(user);
			return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	public ResponseEntity<Void> delete(@PathVariable("id") String id){
		try {
			userRepository.deleteById(Long.valueOf(id));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
