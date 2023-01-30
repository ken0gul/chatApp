package com.ogulcan.chatapp.domain;

import java.util.Objects;

public class User {

	private String username;

	public User(String username) {
		this.username = username;
	}
	
	public String getName() {
		return username;
	}

	public void setName(String name) {
		this.username = name;
	}

	@Override
	public String toString() {
		return "User [username=" + username + "]";
	}


	
	
}
