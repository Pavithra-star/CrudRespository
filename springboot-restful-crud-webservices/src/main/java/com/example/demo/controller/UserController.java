package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceNotFoundExceptions;
import com.example.demo.repo.UserRepo;

@RestController
//@ResponseBody @Controller
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepo userRepo;

	// get all users

	@GetMapping("/getall")
	public List<User> getAllUSer() {
		return this.userRepo.findAll();
	}

	// get USer By id;
	@GetMapping("/{id}")
	public User getByID(@PathVariable(value = "id") long id) {
		return this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundExceptions(" user not found with id :" + id));
	}
	// create user

	@PostMapping("/create")
	public User createUSer(@RequestBody User user) {
		return this.userRepo.save(user);
	}
	// update user

	@PutMapping("/{id}")
	public User updateUser(@PathVariable(value = "id") long id, @RequestBody User user) {
		User existUser = this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundExceptions("user not found with id: " + id));
		existUser.setFirstName(user.getFirstName());
		existUser.setLastName(user.getLastName());
		existUser.setEmail(user.getEmail());
		return this.userRepo.save(existUser);
	}

	// delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteById(@PathVariable(value = "id") long id) {
		User existUSer = this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundExceptions("user not found with id :" + id));
		this.userRepo.delete(existUSer);
		return ResponseEntity.ok().build();

	}

}
