package com.ngo.khawb.controller;

import com.ngo.khawb.model.User;
import com.ngo.khawb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
  @Autowired private UserService userService;

  @GetMapping("/my-profile")
  public ResponseEntity<Object> getData(Principal p) {
    User user = userService.getDataByEmailId(p.getName());
    LOGGER.info("fetch user data {}", user);
    return ResponseEntity.ok(user);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Object> updateProfile(
      Principal p, @PathVariable("id") long id, @RequestBody User user) {
    LOGGER.info("controller called!");
    User getData = userService.getDataByEmailId(p.getName());
    LOGGER.info("user id is {} and data {}", id, getData);
    userService.updateUserData(user, getData.getId());
    return ResponseEntity.ok(user);
  }
}
