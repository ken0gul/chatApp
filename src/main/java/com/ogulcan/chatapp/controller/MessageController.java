package com.ogulcan.chatapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ogulcan.chatApp.tempStorage.UserTempStorage;
import com.ogulcan.chatapp.domain.Message;
import com.ogulcan.chatapp.domain.User;
import com.ogulcan.chatapp.model.MessageModel;
import com.ogulcan.chatapp.service.MessageService;
import com.ogulcan.chatapp.service.UserService;

@RestController
public class MessageController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService service;

//	private UserTempStorage storage = new UserTempStorage();
	

	@MessageMapping("/chat/{to}")
	@SendTo("/topic/messages/{to}")
	public MessageModel sendMessage(@DestinationVariable String to, @Payload MessageModel message) {

		User user = service.findUserByUsername(message.getMsgFrom());

		User user2 = service.findUserByUsername(to);
		
		
		Message entityMessage = new Message();
		
		entityMessage.setMessage(message.getMessage());
		entityMessage.setFromLogin(message.getMsgFrom());
		entityMessage.setDate(message.getDate());
		entityMessage.setMsgFrom(message.getMsgFrom());
		entityMessage.setReceiver(user2);
		entityMessage.setSender(user);
		
	

		if (user != null && user2 != null) {
			user.getSentMessages().add(entityMessage);
			

			user2.getReceivedMessages().add(entityMessage);
			

			service.saveUser(user);
			service.saveUser(user2);

			
			

			messageService.save(entityMessage);
		}
		return message;

	}

	@GetMapping("/messages")

	public List<Message> getAllMessages() {

		return messageService.getAllMessages();
	}

//	@PostMapping("/remove")
//	
//	public String removeUser(@RequestBody String username)  {
//		System.out.println(username);
//		service.removeUser(username);
//		System.out.println("User " + username + " was removed");
//		
//		return username;
//	}

}
