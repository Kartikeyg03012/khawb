package com.ngo.khawb.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class MailModel {
	private String to;
	private String subject;
	private String message;

}
