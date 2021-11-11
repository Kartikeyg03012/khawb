package com.ngo.khawb.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class SOS {
  private String name;
  private String email;
  private long number;
  private int pinCode;
  private String reason;
  private String message;
  private String additionalDetail;
}
