package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.repositories.UserRepository;

@RestController
@RequestMapping(value="/hateoas/users")
public class OrderHateoasController {

	@Autowired	
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@GetMapping("/{userId}/orders")
	public CollectionModel<Order> getAllOrders(@PathVariable Long userId) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		List<Order> allOrders = userOptional.get().getOrders();
		CollectionModel<Order> finalResources = new CollectionModel<Order>(allOrders);
		return finalResources;

	}

}
