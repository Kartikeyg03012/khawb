package com.ngo.khawb.service.impl;

import com.ngo.khawb.model.MailModel;
import com.ngo.khawb.model.User;
import com.ngo.khawb.model.UserRoles;
import com.ngo.khawb.repository.UserRepository;
import com.ngo.khawb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @Autowired private MailService mailService;

  @Override
  public User addUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(UserRoles.ROLE_END_USER.toString());
    user.setCreated_date(new Date().toGMTString());
    // sending mail for verification.....
    String token = mailService.generateRandomString(20);
    String URL = "http://localhost:1830/KhawB/user-verification/" + token;
    System.out.println(URL);

    String subject = "Verification Link | KhawB-The NGO";
    String message =
        "Dear "
            + user.getName()
            + ", \n Thanks for reaching out. Glad to be in your network. Look forward to staying in touch.\n"
            + "Your Verification Link is given below: \n\n"
            + URL;
    MailModel mailModel = new MailModel(user.getEmail(), subject, message);

    // call service for sending mail
    mailService.sendingMail(mailModel);
    // set the token in database for further use
    user.setToken(token);

    User save = userRepository.save(user);
    return save;
  }

  @Override
  public List<User> getAllUser() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User getDataByEmailId(String email) {
    return userRepository.getUserByEmail(email);
  }

  @Override
  public User updateUserData(User data, long id) {
    User user = userRepository.getById((int) id);
    data.setId(user.getId());
    return userRepository.save(data);
  }

  @Override
  public void DeleteAccount(long id) {
    userRepository.delete(userRepository.getById((int) id));
  }

  @Override
  public User getByID(long id) {
    return userRepository.getById((int) id);
  }

  @Override
  public User getUserDataByToken(String token) {
    User usernameByEmail = userRepository.getUsernameByToken(token);
    return usernameByEmail;
  }

  @Override
  public Page<User> getAll(Pageable pageable) {
    return userRepository.findAllUsers(pageable);
  }

  @Override
  public Page<User> getAllBplUsers(Pageable pageable) {
    return userRepository.getAllBplUsers(pageable);
  }
}
