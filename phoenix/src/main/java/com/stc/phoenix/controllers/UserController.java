package com.stc.phoenix.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.stc.phoenix.model.User;
import com.stc.phoenix.services.DashboardServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	DashboardServiceImpl dashboardService;

	@ApiOperation(value = "Fetches all users in the database.", response = User.class)
	@GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		return dashboardService.getAllUsers();
	}

	@SuppressWarnings("deprecation")
	@ApiOperation(value = "Fetches a single user by its id.", response = User.class)
	@GetMapping(path = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<User> getUser(@PathVariable int id) {
		User user = dashboardService.findById(id);
		
		//HATEOAS
		EntityModel<User> model = new EntityModel<User>(user);
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getAllUsers());
		model.add(linkTo.withRel("all-users"));
		return model;
	}

	@ApiOperation(value = "Creates user.", response = User.class)
	@PostMapping(path = "/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = dashboardService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@ApiOperation(value = "Handles the deletion of a single user by its id.", response = User.class)
	@DeleteMapping(path = "/users/{id}")
	public void deleteUser(@PathVariable int id) {
		dashboardService.deleteById(id);
	}
	
	@ApiOperation(value = "Update user.", response = User.class)
	@PutMapping(path = "/users/{id}")
	public ResponseEntity<Object> updateUser(@Valid @RequestBody User user, @PathVariable int id) {
		dashboardService.findById(id);
		user.setId(id);
		User savedUser = dashboardService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
