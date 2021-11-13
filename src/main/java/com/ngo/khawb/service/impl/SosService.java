package com.ngo.khawb.service.impl;

import com.ngo.khawb.model.MailModel;
import com.ngo.khawb.model.SOS;
import com.ngo.khawb.model.User;
import com.ngo.khawb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SosService {
  @Autowired private UserRepository userRepository;
  @Autowired private MailService mailService;
  @Autowired private SmsSendingService smsSendingService;

  public void notifyAllUsers(SOS sos) {
    List<User> users = userRepository.getByPinCode(sos.getPinCode());
    String subject = "IMPORTANT || KhawB";
    String message =
        "Dear KhawB User, SOMEONE NEED YOUR HELP NEAR YOU, Details are following below:<br/>"
            + "<br/>Name: "
            + sos.getName()
            + "<br/>Email: "
            + sos.getEmail()
            + "<br/>Contact Number: "
            + sos.getNumber()
            + "<br/>Reason: "
            + sos.getReason()
            + "<br/>Message: "
            + sos.getMessage()
            + "<br/>Additional Information: "
            + sos.getAdditionalDetail()
            + "<br/><br/>If possible please try to help him/her. <br/>very thankful to you,<br/><br/> regards KhawB-The NGO ";

    MailModel mailModel = new MailModel();
    mailModel.setSubject(subject);
    mailModel.setMessage(message);

    for (User u : users) {
      mailModel.setTo(u.getEmail());
      mailService.sendingMail(mailModel);
      smsSendingService.sendSms(message, String.valueOf(u.getNumber()));
    }
  }
}
