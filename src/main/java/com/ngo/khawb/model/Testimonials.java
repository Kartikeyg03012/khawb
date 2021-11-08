package com.ngo.khawb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_testimonials")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Testimonials {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String title;
  private String description;
  private String image_url;
  @ManyToOne @JsonIgnore private User user;

  @ManyToOne @JsonIgnore private Dreams dream;
}
