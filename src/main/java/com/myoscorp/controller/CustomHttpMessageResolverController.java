package com.myoscorp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myoscorp.exception.UserNotFoundException;
import com.myoscorp.model.User;
import com.myoscorp.repository.UserRepository;

@RestController
@RequestMapping("userconverter")
public class CustomHttpMessageResolverController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(path="user",method=RequestMethod.POST ,consumes="text/user")
	public ResponseEntity<Void> addUser(@RequestBody User user) {
		try {
			User createdUser=userRepository.save(user);
			HttpHeaders headers= new HttpHeaders();
			headers.add("id", createdUser.getUserId().toString());
			return new ResponseEntity<>(headers,HttpStatus.CREATED);

		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping(path="user/{id}",method=RequestMethod.GET,produces="text/user")
	public ResponseEntity<User> get(@PathVariable ("id" )String id) throws UserNotFoundException{
		try {
		Optional<User> user= userRepository.findById(Long.valueOf(id));
		return new ResponseEntity<User>(user.get(),HttpStatus.OK);
		} catch(Exception e) {
			throw new UserNotFoundException("User Not Found for id :" +id);
		}
	}
}
