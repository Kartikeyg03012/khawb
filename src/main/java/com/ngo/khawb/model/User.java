package com.ngo.khawb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "user_data")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String email;
	private String name;
	private long number;
	private String password;
	private String token;
	private String created_date;
	private String role;
	private boolean verifiedStatus = true;
	private boolean archive = false;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public User(long id, String email, String name, long number, String password, String token, String created_date,
			String role, boolean verifiedStatus, boolean archive) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.number = number;
		this.password = password;
		this.token = token;
		this.created_date = created_date;
		this.role = role;
		this.verifiedStatus = verifiedStatus;
		this.archive = archive;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getNumber() {
		return number;
	}


	public void setNumber(long number) {
		this.number = number;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getCreated_date() {
		return created_date;
	}


	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public boolean isVerifiedStatus() {
		return verifiedStatus;
	}


	public void setVerifiedStatus(boolean verifiedStatus) {
		this.verifiedStatus = verifiedStatus;
	}


	public boolean isArchive() {
		return archive;
	}


	public void setArchive(boolean archive) {
		this.archive = archive;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", number=" + number + ", password="
				+ password + ", token=" + token + ", created_date=" + created_date + ", role=" + role
				+ ", verifiedStatus=" + verifiedStatus + ", archive=" + archive + "]";
	}
	
	
	
	
	
	
	
	
}
