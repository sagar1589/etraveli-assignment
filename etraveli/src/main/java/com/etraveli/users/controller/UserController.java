package com.etraveli.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etraveli.users.models.UserDetail;
import com.etraveli.users.service.UserService;

/**
 * 
 */
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	/**
	 * creates user entity with mapped preferences.
	 * @param user
	 * @return
	 */
	@PostMapping
	public UserDetail createUser(@Validated @RequestBody UserDetail user) {
		
		return userService.createUpdateUser(user, false);
	}
	
	/**
	 * Updates the existing user details.
	 * @param user
	 * @return
	 */
	@PutMapping
	public UserDetail updateUser(@Validated @RequestBody UserDetail user) {
		
		return userService.createUpdateUser(user, true);
	}
	
	/**
	 * Deletes the user entry from db for given id.
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {		
		if (userService.deleteUser(id)) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
	}
	
	
	/**
	 * Gets the user details of provide user id.
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public UserDetail getUser(@PathVariable("id") Long id) {		
		return userService.getUser(id);
	}

}
