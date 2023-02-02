package com.ogulcan.chatapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogulcan.chatapp.domain.Message;
import com.ogulcan.chatapp.repo.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository repository;
	
	
	public Message save(Message messages) {
		return repository.save(messages);
	}
	
	public List<Message> getAllMessages() {
		return repository.findAll();
	}
	
	public List<Message> getUserMessages(Long senderId, Long receiverId) {
		return repository.findBySenderIdOrReceiverId(senderId, receiverId);
	}

}
