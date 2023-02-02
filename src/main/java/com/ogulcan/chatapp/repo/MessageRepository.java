package com.ogulcan.chatapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ogulcan.chatapp.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

	
	
	
	
	
	
//	private List<String[]> myList = new ArrayList<>();
//	
//	public boolean save(String[] messages) {
//		myList.add(messages);
//	myList.forEach(i-> System.out.println(Arrays.toString(i)));
//		return true;
//	}
//	
//	
//	public List<String[]> getAllMessages(){
//		return myList;
//	}
}
