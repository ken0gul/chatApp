package com.ogulcan.chatapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ogulcan.chatapp.model.Message;
import com.ogulcan.chatapp.service.MessageService;
import com.ogulcan.tempStorage.UserTempStorage;

@RestController
public class MessageController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private MessageService messageService;

	@MessageMapping("/chat/{to}")
	public void sendMessage(@DestinationVariable String to, Message message) {

		String[] newArr = new String[4];
		newArr[0] = to;
		newArr[1] = message.getMessage();
		newArr[2] = message.getDate().toString();
		newArr[3] = message.getFromLogin();

		messageService.save(newArr);

		boolean isExists = UserTempStorage.getInstance().getUsers().contains(to);

		if (isExists) {
			messagingTemplate.convertAndSend("/topic/messages/" + to, message);
		}

	}

	@GetMapping("/messages")

	public List<String[]> getAllMessages() {
		return messageService.getAllMessages();
	}

}
