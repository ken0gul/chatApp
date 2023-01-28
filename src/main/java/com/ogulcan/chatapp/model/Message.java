package com.ogulcan.chatapp.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message {

	private String message;
	private String msgFrom;
	private String date;
	
	
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

	

}
