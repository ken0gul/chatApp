package com.ogulcan.tempStorage;

import java.util.HashSet;
import java.util.Set;

public class UserTempStorage {

	private static UserTempStorage instance;
	private Set<String> users;
	
	private UserTempStorage() {
		users = new HashSet<String>();
	}
	
	public static synchronized UserTempStorage getInstance() {
		if(instance == null) {
			instance = new UserTempStorage();
		}
		
		return instance;
		
	}
	
	
	public Set<String> getUsers() {
		return users;
	}
	
	public void setUser(String username) throws Exception {
		if(users.contains(username)) {
			throw new Exception("User already exists with login: " + username);
		} 
		
		users.add(username);
			
		
	}
}
