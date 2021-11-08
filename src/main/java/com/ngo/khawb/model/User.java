package com.ngo.khawb.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_data")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

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
  private String created_date;

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

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  private List<Dreams> dreams = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  private List<Testimonials> testimonials = new ArrayList<>();

  @ElementCollection private List<Integer> wishToDonate = new ArrayList<>();
}
