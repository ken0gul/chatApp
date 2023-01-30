package com.ogulcan.chatapp.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ogulcan.chatApp.tempStorage.UserTempStorage;
import com.ogulcan.chatapp.model.Message;
import com.ogulcan.chatapp.service.MessageService;

@RestController
public class MessageController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private MessageService messageService;
	
//	private UserTempStorage storage = new UserTempStorage();
	
	@MessageMapping("/chat/{to}")
	public void sendMessage(@DestinationVariable String to, Message message) {
		
		
		
		

		String[] newArr = new String[4];
		newArr[0] = to;
		newArr[1] = message.getMessage();
		newArr[2] = message.getDate().toString();
		newArr[3] = message.getFromLogin();

		messageService.save(newArr);
	
//		boolean isExists = UserTempStorage.getInstance().getUsers().contains(to);
		boolean isExists = UserTempStorage.getInstance().getUsers().contains(to);

		if (isExists) {
			messagingTemplate.convertAndSend("/topic/messages/" + to, message);
		}

	}

	@GetMapping("/messages")

	public List<String[]> getAllMessages() {
		return messageService.getAllMessages();
	}
	
	@PostMapping("/fetchAllUsers")
	
	public String removeUser(@RequestBody String username) throws UnsupportedEncodingException {
//		 Thread thr = new Thread(()-> {
//			 
//			 while(true) {
//				 try {
//					 Thread.sleep(1000);
//					 UserTempStorage.getInstance().removeUserByUsername(username);
//				 }catch(InterruptedException e) {
//					 e.printStackTrace();
//				 }
//			 }
//		 });
//		 thr.start();
		String encodedString = URLEncoder.encode(username, "UTF-8");
		String decodedString = URLDecoder.decode(encodedString, "UTF-8");

		UserTempStorage.getInstance().removeUserByUsername(decodedString);
		
		return username;
	}

}
