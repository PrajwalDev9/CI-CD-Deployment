package com.jwt.example.entity;

import java.util.Random;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserDTOrequest {

	private String username;
	private String email;
	private String role;

	public static UserInfo fromDTo(UserDTOrequest dtOrequest) {
		UserInfo newUser = new UserInfo();
		newUser.setUsername(dtOrequest.getUsername());
		newUser.setEmail(dtOrequest.getEmail());
		newUser.setRole(("ROLE_" + dtOrequest.getRole()).toUpperCase());
		Random random = new Random();
		int randomNumber = random.nextInt(900) + 100;
		String password = dtOrequest.getUsername() + "@" + randomNumber;
		newUser.setPassword(password);
		System.out.println(password);
		return newUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserDTOrequest(String username, String email, String role) {
		super();
		this.username = username;
		this.email = email;
		this.role = role;
	}

	public UserDTOrequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}