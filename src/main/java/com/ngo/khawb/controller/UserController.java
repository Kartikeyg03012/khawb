package com.ngo.khawb.controller;

import com.ngo.khawb.model.Dreams;
import com.ngo.khawb.model.User;
import com.ngo.khawb.service.DreamService;
import com.ngo.khawb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
  @Autowired private UserService userService;
  @Autowired private DreamService dreamService;
  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @GetMapping("/my-profile")
  public ResponseEntity<Object> getData(Principal p) {
    User user = userService.getDataByEmailId(p.getName());
    LOGGER.info("fetch user data {}", user);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/add-profile")
  public void addProfile(@RequestBody User user, @RequestParam("image") MultipartFile file)
      throws IOException {
    LOGGER.info("controller called!");
    String img_name = file.getOriginalFilename();
    user.setUserImageUrl(img_name);
    File saveFile = new ClassPathResource("static/img").getFile();
    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + img_name);
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    userService.addUser(user);
    System.out.println("Image Uploaded SuccessFully");
  }

  @PostMapping("/add-id")
  public void addGovernmentId(@RequestBody User user, @RequestParam("image") MultipartFile file)
      throws IOException {
    LOGGER.info("controller called!");
    String img_name = file.getOriginalFilename();
    user.setGovernmentIdImageUrl(img_name);
    File saveFile = new ClassPathResource("static/img").getFile();
    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + img_name);
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    userService.addUser(user);
    System.out.println("Image Uploaded SuccessFully");
  }

  @PostMapping("/add-bpl")
  public void addBplCard(@RequestBody User user, @RequestParam("image") MultipartFile file)
      throws IOException {
    LOGGER.info("controller called!");
    String img_name = file.getOriginalFilename();
    user.setBplCardUrl(img_name);
    File saveFile = new ClassPathResource("static/img").getFile();
    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + img_name);
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    userService.addUser(user);
    System.out.println("Image Uploaded SuccessFully");
  }

  @PostMapping("/update-profile/{id}")
  public String updatePage(Principal p, @PathVariable("id") long id, Model model) {
    User user = userService.getDataByEmailId(p.getName());
    model.addAttribute("data", user);
    return "update-profile";
  }

  @PostMapping("/update")
  public String updateProfile(
      Principal p,
      @ModelAttribute User user,
      @RequestParam("profile") MultipartFile profile,
      @RequestParam("govtId") MultipartFile govtId,
      @RequestParam("bpl") MultipartFile bpl,
      Model model,
      HttpSession session)
      throws IOException {
    LOGGER.info("controller called!");
    User getData = userService.getDataByEmailId(p.getName());
    LOGGER.info("user id is {} and data {}", user.getId(), getData);
    if (getData.getId() == user.getId()) {
      if (profile.isEmpty()) {
        user.setUserImageUrl(getData.getUserImageUrl());
      } else {
        String img_name = profile.getOriginalFilename();
        user.setUserImageUrl(img_name);
        File saveFile = new ClassPathResource("static/img").getFile();
        Path path =
            Paths.get(saveFile.getAbsolutePath() + File.separator + user.getId() + img_name);
        Files.copy(profile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Image Uploaded SuccessFully: " + path);
      }
      if (govtId.isEmpty()) {
        user.setGovernmentIdImageUrl(getData.getGovernmentIdImageUrl());
      } else {
        String img_name2 = govtId.getOriginalFilename();
        user.setGovernmentIdImageUrl(img_name2);
        File saveFile2 = new ClassPathResource("static/img").getFile();
        Path path2 =
            Paths.get(saveFile2.getAbsolutePath() + File.separator + user.getId() + img_name2);
        Files.copy(govtId.getInputStream(), path2, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Image Uploaded SuccessFully: " + path2);
      }
      if (!bpl.isEmpty()) {
        String img_name3 = bpl.getOriginalFilename();
        user.setBplCardUrl(img_name3);
        File saveFile3 = new ClassPathResource("static/img").getFile();
        Path path3 =
            Paths.get(saveFile3.getAbsolutePath() + File.separator + user.getId() + img_name3);
        Files.copy(bpl.getInputStream(), path3, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Image Uploaded SuccessFully: " + path3);
      }

      user.setRole(getData.getRole());
      user.setArchive(getData.isArchive());
      user.setToken(getData.getToken());
      user.setDreamer(getData.isDreamer());
      user.setWishToDonate(getData.getWishToDonate());
      user.setVerifiedStatus(getData.isVerifiedStatus());
      user.setCreated_date(getData.getCreated_date());
      user.setVerifyByAdmin(getData.isVerifyByAdmin());
      user.setFlagCount(getData.getFlagCount());
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.setDreams(getData.getDreams());

      User updateUserData = userService.updateUserData(user, getData.getId());
      model.addAttribute("data", updateUserData);
      session.setAttribute("type", "alert-success");
      session.setAttribute("msg", "Success Fully Updated!");
    } else {
      model.addAttribute("data", getData);
      session.setAttribute("type", "alert-danger");
      session.setAttribute("msg", "Something Went Wrong!");
    }
    return "redirect:/user/dashboard";
  }

  @GetMapping("/dashboard")
  public String dashboard(Model model, Principal p) {
    User user = userService.getDataByEmailId(p.getName());
    List<Dreams> list = user.getDreams();

    Iterable<Long> wishlist = user.getWishToDonate();
    if (wishlist != null) {
      List<Dreams> wishListProducts = dreamService.getWishListProducts(wishlist);
      for (Dreams dreams : wishListProducts) {
        if (!dreams.isArchive()) {
          model.addAttribute("wishlist", wishListProducts);
        }
      }

      if (user.isAdmin()) {
        Pageable pageable = PageRequest.of(0, 50);
        Page<Dreams> allDreams = dreamService.getAll(pageable);
        Page<User> allUsers = userService.getAll(pageable);
        Page<User> bplUsers = userService.getAllBplUsers(pageable);

        model.addAttribute("allDreams", allDreams);
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("bplUsers", bplUsers);
        model.addAttribute("list", list);
        model.addAttribute("data", user);
      }
      model.addAttribute("list", list);
      model.addAttribute("data", user);
    }
    return "user-pages/dashboard";
  }

  @GetMapping("/verified/{id}")
  public String verifyByAdmin(
      Principal p, @PathVariable("id") long id, Model model, HttpSession session) {
    User user = userService.getDataByEmailId(p.getName());
    if (user.isAdmin()) {
      User byID = userService.getByID(id);

      if (byID.isVerifyByAdmin()) {
        byID.setVerifyByAdmin(false);
      } else {
        byID.setVerifyByAdmin(true);
      }

      userService.updateUserData(byID, byID.getId());
    }
    session.setAttribute("type", "alert-success");
    session.setAttribute("msg", "Success Fully Updated!");
    return "redirect:/user/dashboard";
  }

  @GetMapping("/archive/{id}")
  public String archiveByAdmin(
      Principal p, @PathVariable("id") long id, Model model, HttpSession session) {
    User user = userService.getDataByEmailId(p.getName());
    if (user.isAdmin()) {
      User byID = userService.getByID(id);

      if (byID.isArchive()) {
        byID.setArchive(false);
      } else {
        byID.setArchive(true);
      }

      userService.updateUserData(byID, byID.getId());
    }
    session.setAttribute("type", "alert-success");
    session.setAttribute("msg", "Success Fully Updated!");
    return "redirect:/user/dashboard";
  }

  @GetMapping("/bpl-verify/{id}")
  public String bplVerify(Principal p, @PathVariable("id") long id, HttpSession session) {
    User user = userService.getDataByEmailId(p.getName());
    System.out.println("Controller called");
    if (user.isAdmin()) {
      User byID = userService.getByID(id);
      if (byID.isDreamer()) {
        byID.setDreamer(false);
      } else {
        byID.setDreamer(true);
      }
      userService.updateUserData(byID, byID.getId());
    }
    session.setAttribute("type", "alert-success");
    session.setAttribute("msg", "Success Fully Updated!");
    return "redirect:/user/dashboard";
  }
}
