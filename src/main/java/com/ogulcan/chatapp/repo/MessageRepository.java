package com.ogulcan.chatapp.repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {

	
	private List<String[]> myList = new ArrayList<>();
	
	public boolean save(String[] messages) {
		myList.add(messages);
	myList.forEach(i-> System.out.println(Arrays.toString(i)));
		return true;
	}
	
	
	public List<String[]> getAllMessages(){
		return myList;
	}
}
