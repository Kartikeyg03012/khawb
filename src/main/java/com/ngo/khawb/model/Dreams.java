package com.ngo.khawb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_dreams")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Dreams {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotBlank(message = "Title can't be blank")
  private String title;

  @NotBlank(message = "Description can't be blank")
  private String description;

  @NotBlank(message = "Paid can't be blank")
  private boolean paid;

  private Long amount;
  private boolean adminVerified;
  private String status;
  private int reportCount;
  private int upVote;

  @ManyToOne
  @JsonIgnore
  @NotBlank(message = "User can't be blank")
  private User user;
}
