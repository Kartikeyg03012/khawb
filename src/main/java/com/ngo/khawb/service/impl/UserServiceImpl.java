package com.ngo.khawb.service.impl;

import com.ngo.khawb.model.User;
import com.ngo.khawb.model.UserRoles;
import com.ngo.khawb.repository.UserRepository;
import com.ngo.khawb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @Override
  public User addUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(UserRoles.ROLE_SUPER_ADMIN.toString());
    user.setCreated_date(new Date().toGMTString());
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
    User user = userRepository.getUserByEmail(data.getEmail());
    data.setId(user.getId());
    data.setName(user.getName());
    data.setNumber(user.getNumber());
    data.setPassword(user.getPassword());
    data.setBplCardUrl(user.getBplCardUrl());
    data.setGovernmentIdImageUrl(user.getGovernmentIdImageUrl());
    return userRepository.save(data);
  }

  @Override
  public void DeleteAccount(long id) {
  userRepository.delete(userRepository.getById((int) id));
  }
}
