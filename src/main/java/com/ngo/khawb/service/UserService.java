package com.ngo.khawb.service;

import com.ngo.khawb.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

  User addUser(User user);

  List<User> getAllUser();

  User getDataByEmailId(String email);

  User updateUserData(User user, long id);

  void DeleteAccount(long id);

  User getByID(long id);

  User getUserDataByToken(String token);

  Page<User> getAll(Pageable pageable);

  Page<User> getAllBplUsers(Pageable pageable);
}
