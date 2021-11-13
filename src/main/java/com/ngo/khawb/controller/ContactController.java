package com.ngo.khawb.controller;

import com.ngo.khawb.model.ContactUsModel;
import com.ngo.khawb.model.MailModel;
import com.ngo.khawb.service.ContactUsService;
import com.ngo.khawb.service.DreamService;
import com.ngo.khawb.service.UserService;
import com.ngo.khawb.service.impl.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping()
public class ContactController {
  @Autowired private UserService userService;
  @Autowired private DreamService dreamService;
  @Autowired private MailService mailService;
  @Autowired private ContactUsService contactUsService;

  @PostMapping("/contact-us")
  public String contactUs(
      @ModelAttribute ContactUsModel contactUsModel, Model model, HttpSession session) {
    try {
      // Save Query In Database
      contactUsModel.setStatus("Process");
      contactUsModel.setCreate_date(new Date().toLocaleString());
      contactUsService.addQuery(contactUsModel);

      String subject = "KhawB-The NGO || Query Genrated";
      String message =
          "<h1>Welcome To KhawB</h1><br/>Dear "
              + contactUsModel.getName()
              + ", <br/>We Recive Your Query, and Our team will connect with you sortly!!<br/>"
              + "Thanks For Connecting With us<br/>Regards<br/>KhawB-The NGO";
      MailModel mail = new MailModel(contactUsModel.getEmail(), subject, message);
      mailService.sendingMail(mail);

      session.setAttribute("type", "alert-success");
      session.setAttribute("msg", "We Get Your Request,Our Team Will Reach You Soon!!");
    } catch (Exception e) {
      session.setAttribute("type", "alert-danger");
      session.setAttribute("msg", "Invalid Request...");
    }
    return "redirect:/home";
  }
}
