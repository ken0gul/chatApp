package com.ogulcan.chatapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogulcan.chatapp.repo.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository repository;
	
	
	public boolean save(String[] messages) {
		return repository.save(messages);
	}
	
	public List<String[]> getAllMessages() {
		return repository.getAllMessages();
	}
}
