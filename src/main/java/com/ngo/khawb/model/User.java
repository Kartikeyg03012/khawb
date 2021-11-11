package com.ngo.khawb.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_data")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(unique = true)
  @NotBlank(message = "Email can't be blank")
  private String email;

  @NotBlank(message = "Name can't be blank")
  private String name;

  @Column(unique = true)
  @NotBlank(message = "Number can't be blank")
  private long number;

  @NotBlank(message = "Password can't be blank")
  private String password;

  private String token;
  private String created_date = new Date().toGMTString();

  @NotBlank(message = "Address can't be blank")
  private String address;

  @NotBlank(message = "City can't be blank")
  private String city;

  @NotBlank(message = "State can't be blank")
  private String state;

  @NotBlank(message = "PinCode can't be blank")
  private int pinCode;

  private String role;

  @NotBlank(message = "Image can't be blank")
  private String userImageUrl;

  @NotBlank(message = "Government Id can't be blank")
  private String governmentIdImageUrl;

  private String bplCardUrl;

  private boolean verifiedStatus = true;
  private boolean verifyByAdmin = true;
  private boolean archive = false;
  private int flagCount;
  private boolean dreamer;
  private boolean admin;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  private List<Dreams> dreams = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  private List<Testimonials> testimonials = new ArrayList<>();

  @ElementCollection private List<Long> wishToDonate = new ArrayList<>();
}
