package com.ngo.khawb.controller;

import com.ngo.khawb.model.DreamStatus;
import com.ngo.khawb.model.Dreams;
import com.ngo.khawb.model.User;
import com.ngo.khawb.service.DreamService;
import com.ngo.khawb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user-dream")
public class DreamController {
  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
  @Autowired private DreamService dreamService;
  @Autowired private UserService userService;

  @GetMapping("/{id}")
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
  public String addDream(
      @ModelAttribute Dreams dream, Model model, Principal p, HttpSession session) {
    LOGGER.info("data send by the dream: {}", dream);
    User data = userService.getDataByEmailId(p.getName());
    Dreams save = new Dreams();
    dream.setUser(data);
    dream.setStatus("Approved Pending");
    dream.setAdminVerified(false);
    dream.setReportCount(0);
    dream.setUpVote(0);
    dream.setArchive(false);
    if (dream.isPaid()) {
      if (data.isDreamer()) {
        save = dreamService.addDreams(dream);
        session.setAttribute("type", "alert-success");
        session.setAttribute("msg", "Successfully Added ");
      } else {
        LOGGER.warn("Something went wrong");
        session.setAttribute("type", "alert-danger");
        session.setAttribute(
            "msg",
            "Profile not completed for demanding any financial help OR Profile Under Verification!");
      }
    } else {
      save = dreamService.addDreams(dream);
      session.setAttribute("type", "alert-success");
      session.setAttribute("msg", "Successfully Added!");
    }
    return "redirect:/user/dashboard";
  }

  @GetMapping("/my-dreams")
  public ResponseEntity<List<Dreams>> getAllDreams(Principal p) {
    User user = userService.getDataByEmailId(p.getName());
    if (!user.getDreams().isEmpty()) {
      LOGGER.info("dreams : {}", user.getDreams());
      return ResponseEntity.ok(user.getDreams());
    } else {
      LOGGER.warn("Dream Not Found with user id {}", user.getId());
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/update-page/{id}")
  public String updatePage(Principal p, @PathVariable("id") long id, Model model) {
    User user = userService.getDataByEmailId(p.getName());
    Dreams dreams = dreamService.getDreamById(id);
    model.addAttribute("data", user);
    model.addAttribute("dream", dreams);
    return "user-pages/updateDream";
  }

  @PostMapping("/update-dream")
  public String updateDream(
      Principal p, @ModelAttribute Dreams dreams, Model model, HttpSession session) {
    LOGGER.info("controller called!");
    User getData = userService.getDataByEmailId(p.getName());
    Dreams oldDreamData = dreamService.getDreamById(dreams.getId());
    dreams.setUser(getData);
    dreams.setArchive(oldDreamData.isArchive());
    dreams.setReportCount(oldDreamData.getReportCount());
    dreams.setStatus(oldDreamData.getStatus());
    dreams.setUpVote(oldDreamData.getUpVote());
    LOGGER.info("dream id is {} and data {}", dreams.getId(), dreams);
    if (dreams != null) {
      dreamService.updateDream(dreams);
      model.addAttribute("data", getData);
      session.setAttribute("type", "alert-success");
      session.setAttribute("msg", "Success Fully Updated!");
    } else {
      model.addAttribute("data", getData);
      session.setAttribute("type", "alert-danger");
      session.setAttribute("msg", "Something Went Wrong!");
    }
    return "redirect:/user/dashboard";
  }

  @GetMapping("/status/{id}/{text}")
  public String markAsComplete(
      Principal p,
      @PathVariable("id") long id,
      @PathVariable("text") String text,
      Model model,
      HttpSession session) {
    User getData = userService.getDataByEmailId(p.getName());
    Dreams dreams = dreamService.getDreamById(id);
    if (getData.getId() == dreams.getUser().getId()) {
      dreams.setStatus(DreamStatus.COMPLETED.name());
      dreams.setArchive(true);
      dreamService.updateDream(dreams);
      model.addAttribute("data", getData);
      session.setAttribute("type", "alert-success");
      session.setAttribute("msg", "Success Fully Updated!");
    } else {
      model.addAttribute("data", getData);
      session.setAttribute("type", "alert-danger");
      session.setAttribute("msg", "Something Went Wrong!");
    }
    return "redirect:/user/dashboard";
  }

  @GetMapping("/remove/{id}")
  public String removeDream(
      Principal p, @PathVariable("id") long id, Model model, HttpSession session) {
    User data = userService.getDataByEmailId(p.getName());
    try {
      Dreams dreams = dreamService.getDreamById(id);
      if (data.getId() == dreams.getUser().getId()) {
        dreams.setUser(null);
        dreamService.deleteDream(dreams);
        System.out.println("Data Deleted with id: " + id);
      } else {
        throw new Exception();
      }
    } catch (Exception e) {
      e.printStackTrace();
      session.setAttribute("type", "alert-danger");
      session.setAttribute("msg", "Something Went Wrong!!!");
    }
    model.addAttribute("data", data);
    return "redirect:/user/dashboard";
  }

  @GetMapping("/verify/{id}")
  public String adminVerify(Principal p, @PathVariable("id") long id, HttpSession session) {
    User user = userService.getDataByEmailId(p.getName());
    if (user.isAdmin()) {
      Dreams byID = dreamService.getDreamById(id);
      byID.setId(id);
      if (byID.isAdminVerified()) {
        byID.setAdminVerified(false);
      } else {
        byID.setAdminVerified(true);
      }
      dreamService.updateDream(byID);
    }
    session.setAttribute("type", "alert-success");
    session.setAttribute("msg", "Success Fully Updated!");
    return "redirect:/user/dashboard";
  }

  @GetMapping("/archive/{id}")
  public String adminArchive(Principal p, @PathVariable("id") long id, HttpSession session) {
    User user = userService.getDataByEmailId(p.getName());
    if (user.isAdmin()) {
      Dreams byID = dreamService.getDreamById(id);
      byID.setId(id);
      if (byID.isArchive()) {
        byID.setArchive(false);
      } else {
        byID.setArchive(true);
      }
      dreamService.updateDream(byID);
    }
    session.setAttribute("type", "alert-success");
    session.setAttribute("msg", "Success Fully Updated!");
    return "redirect:/user/dashboard";
  }

  @GetMapping("/complete/{id}")
  public String completeByAdmin(Principal p, @PathVariable("id") long id, HttpSession session) {
    User user = userService.getDataByEmailId(p.getName());
    if (user.isAdmin()) {
      Dreams byID = dreamService.getDreamById(id);
      byID.setId(id);
      byID.setStatus(DreamStatus.COMPLETED.name());
      dreamService.updateDream(byID);
    }
    session.setAttribute("type", "alert-success");
    session.setAttribute("msg", "Success Fully Updated!");
    return "redirect:/user/dashboard";
  }
}
