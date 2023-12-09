package com.jwt.example.entity;
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import lombok.AllArgsConstructor; 
import lombok.Data; 
import lombok.NoArgsConstructor; 

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo { 


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id; 
	private String username; 
	private String email; 
	private String password; 
	private String role;
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", role=" + role + "]";
	}
	public UserInfo(String username, String email, String password, String role) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	} 
	public UserInfo(int id, String username, String password, String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

} 
