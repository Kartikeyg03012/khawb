package com.ngo.khawb.repository;

import com.ngo.khawb.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("select u from User u WHERE u.email=:email")
  public User getUserByEmail(@Param("email") String email);

  @Query("select u from User u WHERE u.token=:token ")
  public User getUsernameByToken(@Param("token") String token);

  @Query("select u from User u WHERE u.pinCode=:pincode ")
  public List<User> getByPinCode(@Param("pincode") int pincode);

  @Query("from User as u ORDER by id DESC")
  public Page<User> findAllUsers(Pageable pageAble);

  @Query("select u from User u WHERE u.bplCardUrl IS NOT NULL ORDER by id DESC")
  Page<User> getAllBplUsers(Pageable pageable);
}
