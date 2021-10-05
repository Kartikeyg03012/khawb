package com.ngo.khawb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public ResponseEntity<String> showHomePage() {
		return ResponseEntity.ok("Hello Dear, Server is working!");
	}
}
