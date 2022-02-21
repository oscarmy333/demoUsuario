package com.myoscorp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myoscorp.exception.UserNotFoundException;
import com.myoscorp.model.User;
import com.myoscorp.repository.UserRepository;

@RestController
@RequestMapping("v2")
public class HateoasController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(path = "user/{id}",method = RequestMethod.GET)
	public EntityModel<User> get(@PathVariable("id") String id) throws UserNotFoundException{
		try {
			Optional<User> user = userRepository.findById(Long.valueOf(id));
			EntityModel< User> entityModel = EntityModel.of(user.get());
			Link selfLink = WebMvcLinkBuilder
					.linkTo(HateoasController.class)
					.slash(user.get().getUserId())
					.withSelfRel();
			Link deleteLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UserController.class)
							.delete(user.get().getUserId()+""))
					.withRel("delete");
			entityModel.add(selfLink).add(deleteLink);
			return entityModel;
		} catch (NumberFormatException e) {
			throw new UserNotFoundException("Usuario no encontrado con id "+id);
		}
	}
}
