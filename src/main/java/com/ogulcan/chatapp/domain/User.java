package com.ogulcan.chatapp.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private boolean isJoined = false;
	
	
	@OneToMany(mappedBy = "sender",  fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Message> sentMessages;

	
	@OneToMany(mappedBy = "receiver",  fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Message> receivedMessages;
	
	public User() {
		
	}
	
	
	
	
	public boolean isJoined() {
		return isJoined;
	}




	public void setJoined(boolean isJoined) {
		this.isJoined = isJoined;
	}




	public User(String username) {
		this.username=username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", username=" + username + ", password=" + password + ", sentMessages=" + sentMessages
//				+ ", receivedMessages=" + receivedMessages + "]";
//	}

	public List<Message> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}

	public List<Message> getReceivedMessages() {
		return receivedMessages;
	}

	public void setReceivedMessages(List<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}
	

}
