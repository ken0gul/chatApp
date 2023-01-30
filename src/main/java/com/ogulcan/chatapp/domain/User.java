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

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(username, other.username);
	}
	
	
}
