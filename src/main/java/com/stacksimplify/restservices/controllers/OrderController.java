package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exception.OrderNotFoundException;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class OrderController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	// get all orders for a user
	@GetMapping("/{userId}/orders")
	public List<Order> getAllOrders(@PathVariable Long userId) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		return userOptional.get().getOrders();

	}

	// create order
	@PostMapping("/{userId}/orders")
	public Order createOrder(@PathVariable Long userId, @RequestBody Order order) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		User user = userOptional.get();
		order.setUser(user);
		return orderRepository.save(order);

	}

	/// users/{userId}/orders/{orderId}
	// getOrderByOrderId
	@GetMapping("/{userId}/orders/{orderId}")
	public Order getOrderByOrderId(@PathVariable Long userId, @PathVariable Long orderId)
			throws UserNotFoundException, OrderNotFoundException {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		User user = userOptional.get();
		List<Order> orders = user.getOrders().stream().filter(order -> order.getOrderId().equals(orderId))
				.collect(Collectors.toList());
		if (!orders.isEmpty()) {
			Order order = orders.get(0);
			return order;
		}
		throw new OrderNotFoundException("Order with id : "+ orderId + " not found");

	}
}
