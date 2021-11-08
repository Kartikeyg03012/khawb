package com.ngo.khawb.controller;

import com.ngo.khawb.model.User;
import com.ngo.khawb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @Autowired private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Object> getData(Principal p) {
        User user = userService.getDataByEmailId(p.getName());
        LOGGER.info("fetch user data {}", user);
        return ResponseEntity.ok(user);
    }
}
