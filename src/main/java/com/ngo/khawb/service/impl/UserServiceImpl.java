package com.ngo.khawb.service.impl;

import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ngo.khawb.model.User;
import com.ngo.khawb.model.UserRoles;
import com.ngo.khawb.repository.UserRepository;
import com.ngo.khawb.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(UserRoles.ROLE_SUPER_ADMIN.toString());
		user.setCreated_date(new Date().toGMTString());
		User save = userRepository.save(user);
		return save;
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
