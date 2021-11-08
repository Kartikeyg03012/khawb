package com.ngo.khawb.controller;

import com.ngo.khawb.model.Dreams;
import com.ngo.khawb.model.User;
import com.ngo.khawb.service.DreamService;
import com.ngo.khawb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class DreamController {
  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
  @Autowired private DreamService dreamService;
  @Autowired private UserService userService;

  @GetMapping("/my-dream/{id}")
  public ResponseEntity<Object> getDreamById(Principal p, @PathVariable("id") long id) {
    LOGGER.info("Dream id is {}", id);
    User user = userService.getDataByEmailId(p.getName());
    Dreams dream = dreamService.getDreamById(id);
    if (user.getId() == dream.getUser().getId()) {
      LOGGER.info("dream is: {}", dream);
      return ResponseEntity.ok(dream);
    } else {
      LOGGER.warn("Dream Not Found with user id {}", user.getId());
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/add-dream")
  public ResponseEntity<String> addDream(@RequestBody Dreams dream) {
    LOGGER.info("data send by the dream: {}", dream);
    Dreams save = dreamService.addDreams(dream);
    if (save != null) {
      LOGGER.info("dream Created: {}", save);
      return ResponseEntity.ok("Success");
    } else {
      LOGGER.warn("Something went wrong");
      return ResponseEntity.badRequest().body("Something went wrong");
    }
  }

  @GetMapping("/my-dreams")
  public ResponseEntity<Object> getAllDreams(Principal p) {
    User user = userService.getDataByEmailId(p.getName());
    if (!user.getDreams().isEmpty()) {
      LOGGER.info("dreams : {}", user.getDreams());
      return ResponseEntity.ok(user.getDreams());
    } else {
      LOGGER.warn("Dream Not Found with user id {}", user.getId());
      return ResponseEntity.notFound().build();
    }
  }



}
