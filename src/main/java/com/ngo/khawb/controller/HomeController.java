package com.ngo.khawb.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngo.khawb.model.User;
import com.ngo.khawb.service.UserService;

@RestController
@RequestMapping("/")
public class HomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public ResponseEntity<String> showHomePage() {
		LOGGER.info("showing Default page");
		return ResponseEntity.ok("Hello Dear, Server is working!");
	}
	
	@GetMapping("/admin/login-success")
	public ResponseEntity<String> loginPage(HttpSession session) {
		LOGGER.info("Spring Aucth Works Fine");
		return ResponseEntity.ok("Hello "+session.getId()+", login is working!");
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<Object> addUser(@RequestBody User user){
		LOGGER.info("data send by the user: {}",user);
		User save=userService.addUser(user);
		if(save!=null) {
			LOGGER.info("User Created: {}",save);
			return ResponseEntity.ok("Success");
		}else{
			LOGGER.warn("Something went wrong");
			return ResponseEntity.badRequest().body("Something went wrong");
		}
		
	}
}
