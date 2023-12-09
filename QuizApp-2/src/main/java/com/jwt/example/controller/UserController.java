package com.jwt.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.example.entity.AuthRequest;
import com.jwt.example.entity.JwtResponse;
import com.jwt.example.entity.UserDTOrequest;
import com.jwt.example.entity.UserInfo;
import com.jwt.example.repository.UserInfoRepository;
import com.jwt.example.service.JwtService;
import com.jwt.example.service.UserInfoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement; 

@RestController
@RequestMapping("/auth") 
@SecurityRequirement(name="bearerAuth")
public class UserController { 

	@Autowired
	private UserInfoService service; 

	@Autowired
	private JwtService jwtService; 

	@Autowired
	private AuthenticationManager authenticationManager; 

	
	@GetMapping("/welcome") 
	public String welcome() { 
		return "Welcome this endpoint is not secure"; 
	} 

	@PostMapping("/addNewUser") 
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> addNewUser(@RequestBody UserDTOrequest userDTOrequest) { 
		
		UserInfo user = UserDTOrequest.fromDTo(userDTOrequest);
		return new ResponseEntity<String> (service.addUser(user),HttpStatus.CREATED); 
	} 

	@PostMapping("/login") 
	public ResponseEntity<JwtResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
		System.out.println(authRequest);
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 

		if (authentication.isAuthenticated()) { 
	    
			JwtResponse response= new JwtResponse();
			UserInfo user = service.getUserByUsername(authRequest.getUsername());
			response.setJwtToken(jwtService.generateToken(authRequest.getUsername()));
			response.setEmail(user.getEmail());
			response.setRole(user.getRole());
			response.setUsername(user.getUsername());
		
			return new ResponseEntity<JwtResponse>(response,HttpStatus.OK); 
		} else { 
			throw new UsernameNotFoundException("invalid user request !"); 
		} 
	} 

} 
