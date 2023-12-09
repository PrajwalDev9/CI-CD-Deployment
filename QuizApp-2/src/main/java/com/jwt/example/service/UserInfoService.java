package com.jwt.example.service;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

import com.jwt.example.entity.UserInfo;
import com.jwt.example.entity.UserInfoDetails;
import com.jwt.example.repository.UserInfoRepository;

import java.util.Optional; 

@Service
public class UserInfoService implements UserDetailsService { 

	@Autowired
	private UserInfoRepository repository; 

	@Autowired
	private PasswordEncoder encoder; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 

		Optional<UserInfo> userInfo = repository.findByUsername(username); 

		// Converting userDetail to UserDetails 
		return userInfo.map(UserInfoDetails::new) 
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
	} 

	public String addUser(UserInfo userInfo) { 
		userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
		repository.save(userInfo); 
		return "User Added Successfully "; 
	} 

	public UserInfo getUserByUsername(String username) {
		return repository.findByUsername(username).get();
	}
	
	public int findIdByUsername(String username) {
		UserInfo user = repository.findByUsername(username).get();
		if (user != null) {
			return user.getId();
		} else {
			return 0;
		}
	}

	public Optional<UserInfo> getUserById(long id) {
		// TODO Auto-generated method stub
		return repository.findById((int) id);
	}

} 
