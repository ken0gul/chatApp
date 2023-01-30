package com.ogulcan.chatApp.tempStorage;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import com.ogulcan.chatapp.domain.User;

public class UserTempStorage {


	 

	 
	private static UserTempStorage instance;
	private Set<String> users;
	
	private UserTempStorage() {
		users = new HashSet<>();
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
//		for(User u : users) {
			
		if(users.contains(username)) {
			throw new Exception("User already exists with login: " + username);
//		} 
		}
			
			this.users.add(username);
		
			
		
	}
	
	public void removeUserByUsername(String username) {
	    Set<String> tempUsers;
	    synchronized (users) {
	        tempUsers = new HashSet<>(users);
	    }
	    Iterator<String> it = tempUsers.iterator();
	    while (it.hasNext()) {
	        String u = it.next();
	        if (u.equals(username)) {
	            synchronized (users) {
	                users.remove(u);
	            }
	            return;
	        }
	    }
	    throw new NoSuchElementException(username + " not found");
	}
		

	
}
