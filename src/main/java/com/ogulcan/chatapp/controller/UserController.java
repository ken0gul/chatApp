package com.ogulcan.chatapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ogulcan.chatapp.domain.User;
import com.ogulcan.chatapp.service.MessageService;
import com.ogulcan.chatapp.service.UserService;

@Controller
@CrossOrigin
public class UserController {

//	private UserTempStorage storage = new UserTempStorage();

	@Autowired
	private UserService userService;

	
	@Autowired
	private MessageService messageService;
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
//	@GetMapping("")
//	public String getUserPage(ModelMap model) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//		String username = userDetails.getUsername();
//		
//		model.put("username", username);
//		
//		return "index";
//	}

	@GetMapping("/register")
	public String register(ModelMap model) {

		model.put("user", new User());

		return "register";
	}

	@PostMapping("/register")
	public String registerUser(User user, ModelMap model) {
		User usr = new User();
		usr.setUsername(user.getUsername());
		usr.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		User myUser = userService
				.saveUser(usr);

		return "redirect:/login";
	}

	@GetMapping("/register-chat")
	@ResponseBody
	public User enterChat() {
		
		String username = "";
		User user = null;
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			username = userDetails.getUsername();
			System.out.println(username);
			user = userService.findUserByUsername(username);
			user.setJoined(true);
			userService.saveUser(user);

		} catch (Exception e) {
//			return "User is already there";
		
		}

		return user;
	}

	@GetMapping("/fetchAllUsers")
	@ResponseBody
	public List<User> fetchAll() {
	
		return userService.getAllUsers();
	}
}
