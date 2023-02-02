package com.ogulcan.chatapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ogulcan.chatapp.domain.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
