package com.ogulcan.chatapp.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ogulcan.tempStorage.UserStorage;

@RestController
@CrossOrigin
public class UserController {

	@GetMapping("/register/{userName}")
	public ResponseEntity<Void> register(@PathVariable String userName) {
		System.out.println("handling register user request: " + userName);
		try {

			UserStorage.getInstance().setUser(userName);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/fetchAllUsers")
	public Set<String> fetchAll() {
		
		return UserStorage.getInstance().getUsers();
	}
}
