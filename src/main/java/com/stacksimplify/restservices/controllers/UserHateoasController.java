package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;
import com.stacksimplify.restservices.services.UserService;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@GetMapping
	public CollectionModel<User> getAllUsers() throws UserNotFoundException {
		List<User> allUsers = userService.getAllUsers();
		for (User user : allUsers) {
			// Self Link
			Long userId = user.getUserid();
			Link selfLink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
			user.add(selfLink);
			// Relationship link with getAllOrders
			CollectionModel<Order> orders = ControllerLinkBuilder.methodOn(OrderHateoasController.class)
					.getAllOrders(userId);
			Link ordersLink = ControllerLinkBuilder.linkTo(orders).withRel("all-orders");
			user.add(ordersLink);
		}
		// selfLink for getAllUsers
		Link selfLinkGetAllUsers = ControllerLinkBuilder.linkTo(this.getClass()).withSelfRel();
		CollectionModel<User> finalResources = new CollectionModel<User>(allUsers,selfLinkGetAllUsers);
		return finalResources;
	}

	@GetMapping("/{id}")
	public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id) {
		try {
			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();
			Long userid = user.getUserid();
			Link selflink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			user.add(selflink);
			EntityModel<User> finalResource = new EntityModel<User>(user);
			return finalResource;
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

}
