package com.ogulcan.chatapp.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String message;
	private String msgFrom;
	private String date;
	
	@ManyToOne
	@JoinColumn(name = "sender_id")
	@JsonManagedReference
	private User sender;
	
	@ManyToOne
	@JoinColumn(name = "receiver_id")
	@JsonManagedReference
	private User receiver;
	
	public Message() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a");
		String now = dtf.format(LocalTime.now());
		this.date = now;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFromLogin() {
		return msgFrom;
	}

	public void setFromLogin(String fromLogin) {
		this.msgFrom = fromLogin;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsgFrom() {
		return msgFrom;
	}

	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "Message [message=" + message + ", msgFrom=" + msgFrom + ", date=" + date + "]";
	}

	
	

}
