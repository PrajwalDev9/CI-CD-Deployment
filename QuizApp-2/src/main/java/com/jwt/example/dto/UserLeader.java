package com.jwt.example.dto;

public class UserLeader {

	private int id; 
	private String username; 
	private String email;
	private int marks;
	private int totalTimeDuration;
	
	public int getTotalTimeDuration() {
		return totalTimeDuration;
	}
	public void setTotalTimeDuration(int totalTimeDuration) {
		this.totalTimeDuration = totalTimeDuration;
	}
	public UserLeader(int id, String username, String email, int marks, int totalTimeDuration) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.marks = marks;
		this.totalTimeDuration = totalTimeDuration;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public UserLeader(int id, String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}
	public UserLeader() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserLeader [id=" + id + ", username=" + username + ", email=" + email + ", marks=" + marks
				+ ", totalTimeDuration=" + totalTimeDuration + "]";
	} 

	
	
}
