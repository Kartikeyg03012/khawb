package com.ngo.khawb.controller;

import com.ngo.khawb.model.MailModel;
import com.ngo.khawb.model.SOS;
import com.ngo.khawb.model.User;
import com.ngo.khawb.service.UserService;
import com.ngo.khawb.service.impl.MailService;
import com.ngo.khawb.service.impl.SmsSendingService;
import com.ngo.khawb.service.impl.SosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

@Controller
public class HomeController {
  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
  @Autowired private UserService userService;
  @Autowired private MailService mailService;
  @Autowired private SosService sosService;
  @Autowired private SmsSendingService smsService;
  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @GetMapping("/")
  public String indexPage() {
    return "index";
  }

  @GetMapping("/home")
  public String showHomePage(Model model, Principal p) {
    User userData = userService.getDataByEmailId(p.getName());
    LOGGER.info("showing Home page");
    model.addAttribute("data", userData);
    return "homepage";
  }

  @GetMapping("/login-form")
  public String loginForm() {
    return "login";
  }

  @GetMapping("/sign-up")
  public String signUpForm() {
    return "signup";
  }

  @GetMapping("/about")
  public String aboutPage() {
    return "about";
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String addUser(
      @ModelAttribute User user,
      @RequestParam("profile") MultipartFile profile,
      @RequestParam("govtId") MultipartFile govtId,
      Model model,
      HttpSession session)
      throws IOException {
    LOGGER.info("data send by the user: {}", user);
    String img_name = profile.getOriginalFilename();
    user.setUserImageUrl(img_name);
    File saveFile = new ClassPathResource("static/img").getFile();
    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + user.getId() + img_name);
    Files.copy(profile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    System.out.println("Image Uploaded SuccessFully: " + path);

    String img_name2 = govtId.getOriginalFilename();
    user.setGovernmentIdImageUrl(img_name2);
    File saveFile2 = new ClassPathResource("static/img").getFile();
    Path path2 = Paths.get(saveFile2.getAbsolutePath() + File.separator + user.getId() + img_name2);
    Files.copy(govtId.getInputStream(), path2, StandardCopyOption.REPLACE_EXISTING);

    System.out.println("Image Uploaded SuccessFully: " + path2);

    User save = userService.addUser(user);
    if (save != null) {
      LOGGER.info("User Created: {}", save);
      model.addAttribute("data", save);
      session.setAttribute("type", "alert-success");
      session.setAttribute(
          "msg", "Success Fully Register!!!! Kindly check your Email for verification");
    } else {
      LOGGER.warn("Something went wrong");
      session.setAttribute("type", "alert-danger");
      session.setAttribute("msg", "Email is Already Registered!!!");
    }
    return "redirect:/sign-up";
  }

  // Email Verification Of User
  @GetMapping("/user-verification/{token}")
  public ResponseEntity<?> verification(@PathVariable("token") String token, HttpSession session) {

    try {
      User dataByEmail = userService.getUserDataByToken(token);
      if (dataByEmail.equals(null)) {
        throw new UsernameNotFoundException("Invalid User!!!!");
      } else {
        dataByEmail.setVerifiedStatus(true);
        dataByEmail.setToken(null);

        User adduser = userService.updateUserData(dataByEmail, dataByEmail.getId());

        String subject = "KhawB-The NGO || Verification Successful";
        String message =
            "<h1>Welcome To KhawB</h1><br/>Dear "
                + adduser.getName()
                + ", <br/>We are happy to inform you that your Request Has Been Approved Successfully. Now, you can login on our platform.<br/>"
                + "Thanks For Connecting With us<br/>Regards<br/>KhawB-The NGO";
        MailModel mail = new MailModel(adduser.getEmail(), subject, message);
        mailService.sendingMail(mail);
        return ResponseEntity.ok("Verification Successful");
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/sos")
  public ResponseEntity<?> sendSOS(@RequestBody SOS sos) {
    sosService.notifyAllUsers(sos);
    return ResponseEntity.ok("success");
  }

  // Show Forgot Password Page
  @GetMapping("/forgot")
  public String forgotPage() {
    return "show-forgot";
  }

  // Verify The User And Sending OTP On mobile Number....
  @PostMapping("/verify-user")
  public String verifyUser(@RequestParam("email") String email, Model model, HttpSession session) {
    try {
      User dataByEmail = userService.getDataByEmailId(email);
      String actual_OTP = smsService.generateOtp(6);
      String smsMessage =
          "Hello User, \nWelcome To Khawb-The NGO. Your OTP(One Time password) is: "
              + actual_OTP
              + ". Thanks For Connecting With Us, \nRegards- Khawb-The NGO!!!";
      System.out.println(actual_OTP);
      if (email.equalsIgnoreCase(dataByEmail.getEmail())) {
        // System.out.println(dataByEmail.getNumber());
        // Sending OTP via SMS
        // smsService.sendSms(smsMessage, String.valueOf(dataByEmail.getNumber()));
        session.setAttribute("otp", actual_OTP);
        session.setAttribute("email", email);
        session.setAttribute("number", dataByEmail.getNumber());
        return "verify-otp";
      } else {
        throw new UsernameNotFoundException("Something Went Wrong!!!!");
      }

    } catch (Exception e) {
      session.setAttribute("type", "alert-danger");
      session.setAttribute("msg", "Email Is Not Registered Or Invalid");
      return "sign-up";
    }
  }

  // Verify The OTP Send By the User
  @PostMapping("/verify-otp")
  public String verifyOTP(
      @RequestParam("email") String email,
      @RequestParam("myotp") int myotp,
      Model model,
      HttpSession session) {
    System.out.println((String) (session.getAttribute("otp")));
    try {
      int id = Integer.parseInt((String) (session.getAttribute("otp")));
      if (myotp == id) {
        session.setAttribute("email", email);
        return "change-password";
      } else {
        throw new Exception("Invalid OTP");
      }

    } catch (Exception e) {
      session.setAttribute("type", "alert-danger");
      session.setAttribute("msg", "Invalid OTP");
      return "sign-up";
    }
  }

  // Change My Password After Authenticate
  @PostMapping("/change-my-password")
  public String changeMyPassword(
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      @RequestParam("retype_pwd") String retype_pwd,
      Model model,
      HttpSession session) {
    try {
      User getData = userService.getDataByEmailId(email);
      if (!password.equals(retype_pwd) && getData.equals(null)) {
        throw new UsernameNotFoundException("password doesn't match!");
      } else {
        getData.setPassword(passwordEncoder.encode(password));
        User result = userService.updateUserData(getData, getData.getId());

        String subject = "Khawb-The NGO || Password Changed";
        String message =
            "<h1>Welcome To Khawb-The NGO</h1><br/>Dear "
                + getData.getName()
                + ", <br/>Your Password Has Been Changed Successfully. If it was not you please contact to our technical team. <br/>"
                + "Thanks For Connecting With Us<br/>Regards<br/>Khawb-The NGO";
        MailModel mail = new MailModel(getData.getEmail(), subject, message);
        mailService.sendingMail(mail);

        session.setAttribute("type", "alert-success");
        session.setAttribute("msg", "Password Change Successfully");
        return "login-page";
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      session.setAttribute("type", "alert-danger");
      session.setAttribute("msg", "Something Went Wrong! Please Try Again...");
      return "login-page";
    }
  }

  // Sending OTP Using Email
  @GetMapping("/send-otp-email/{email}")
  public String sendOtpOnEmail(
      @PathVariable("email") String email, Model model, HttpSession session) {
    try {
      User dataByEmail = userService.getDataByEmailId(email);
      String actual_OTP = smsService.generateOtp(6);
      String emailMessage =
          "Hello User, \nWelcome To Khawb-The NGO. Your OTP(One Time password) is: " + actual_OTP;
      String subject = "OTP - Forgot Password | Khawb-The NGO";
      System.out.println(actual_OTP);
      if (email.equalsIgnoreCase(dataByEmail.getEmail())) {
        System.out.println(dataByEmail.getEmail());
        // Sending OTP On Email
        MailModel mailModel = new MailModel(dataByEmail.getEmail(), subject, emailMessage);
        mailService.sendingMail(mailModel);
        System.out.println("Sending Mail.....");
        session.setAttribute("otp", actual_OTP);
        session.setAttribute("email", email);
        session.setAttribute("number", dataByEmail.getEmail());
        return "verify-otp";
      } else {
        throw new Exception("Something Went Wrong! Please Try Again...");
      }

    } catch (Exception e) {
      session.setAttribute("type", "alert-danger");
      session.setAttribute("msg", "Email Is Not Registered Or Invalid");
      return "sign-up";
    }
  }
}
