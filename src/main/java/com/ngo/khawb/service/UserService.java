package com.ngo.khawb.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ngo.khawb.model.User;
import com.ngo.khawb.repository.UserRepository;

@Component
public interface UserService {
	
	User addUser(User user);
	
	List<User> getAllUser();
}
