package com.ngo.khawb.repository;

import com.ngo.khawb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("select u from User u WHERE u.email=:email")
  public User getUserByEmail(@Param("email") String email);
}
