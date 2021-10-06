package com.ngo.khawb.security;

import java.nio.file.attribute.UserPrincipalNotFoundException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ngo.khawb.model.User;
import com.ngo.khawb.repository.UserRepository;


public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getUsernameByEmail(username);

		if (user == null || !user.isVerifiedStatus() || user.isArchive()) {
			throw new UsernameNotFoundException("User Not Found");
		}
		UserDetails userDetails = new UserDetailsImpl(user);
		return userDetails;
	}

}
