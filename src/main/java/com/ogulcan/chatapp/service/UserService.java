package com.ogulcan.chatapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogulcan.chatapp.domain.User;
import com.ogulcan.chatapp.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
	
	public User saveUser(User user) {
		return userRepo.save(user);
	}


	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}


	public User findUserByUsername(String fromLogin) {
		return userRepo.findByUsername(fromLogin);
		
	}
}
