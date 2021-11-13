package com.ngo.khawb.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "contact_us_query")
public class ContactUsModel {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String name;
  private String email;
  private String number;
  private String subject;
  private String message;
  private String status;
  private String create_date;
  private String update_date;
}
