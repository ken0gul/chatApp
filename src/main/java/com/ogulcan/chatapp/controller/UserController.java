package com.ogulcan.chatapp.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ogulcan.chatApp.tempStorage.UserTempStorage;

@RestController
@CrossOrigin
public class UserController {

//	private UserTempStorage storage = new UserTempStorage();
	
	@GetMapping("/register/{userName}")
	public ResponseEntity<Void> register(@PathVariable String userName) {
	
		try {

//			UserTempStorage.getInstance().setUser(userName);
			UserTempStorage.getInstance().setUser(userName);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/fetchAllUsers")
	public Set<String> fetchAll() {
		return UserTempStorage.getInstance().getUsers().stream().map(u -> u.getName()).collect(Collectors.toSet());
//		return UserTempStorage.getInstance().getUsers();
//		return storage.getUsers();
	}
}
