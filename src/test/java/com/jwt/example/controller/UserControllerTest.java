package com.jwt.example.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jwt.example.dto.QuizDTO;
import com.jwt.example.entity.AuthRequest;
import com.jwt.example.entity.JwtResponse;
import com.jwt.example.entity.UserDTOrequest;
import com.jwt.example.entity.UserInfo;
import com.jwt.example.repository.UserInfoRepository;
import com.jwt.example.service.JwtService;
import com.jwt.example.service.UserInfoService;

public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserInfoService service;

	@Mock
	private JwtService jwtService;

	@Mock
	private AuthenticationManager authenticationManager;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testWelcome() {
		String resultString = userController.welcome();
		assertThat(resultString).isEqualTo("Welcome this endpoint is not secure");
	}

	@Test
	public void testAddNewUser() {
	
		UserDTOrequest userDTOrequest = new UserDTOrequest("xyz", "xyz@gmail.com", "STUDENT");
	
		when(service.findByUsername(userDTOrequest.getUsername())).thenReturn(null);
		when(service.addUser(any(UserInfo.class))).thenReturn("User added successfully"); 
																							
	
		ResponseEntity<String> responseEntity = userController.addNewUser(userDTOrequest);

		if (userDTOrequest.getEmail().length() == 0 || userDTOrequest.getRole().length() == 0
				|| userDTOrequest.getUsername().length() == 0 || userDTOrequest.getEmail().isBlank()
				|| userDTOrequest.getRole().isBlank() || userDTOrequest.getUsername().isBlank()
				) {
			assertEquals("Please provide a valid data!", responseEntity.getBody());
		} else if (!(userDTOrequest.getEmail().contains("@") && userDTOrequest.getEmail().contains("."))) {
			assertEquals("Please provide a valid email!", responseEntity);
		} else if (userDTOrequest.getRole().equalsIgnoreCase("STUDENT") || userDTOrequest.getRole().equalsIgnoreCase("TRAINER")) {

			assertEquals("User added successfully", responseEntity.getBody());
																
		} else {
			assertEquals("User  already exists", responseEntity.getBody());

		}
	
		verify(service).findByUsername(userDTOrequest.getUsername());
		
	}

	@Test
	public void testAuthenticateAndGetToken() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("abcd");
		authRequest.setPassword("abc@123");

		Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
				authRequest.getPassword());
		Mockito.when(authenticationManager.authenticate(authentication)).thenReturn(authentication);

		UserInfo userInfo = new UserInfo("abcd", "abcd@gmail.com", "abc@123", "ROLE_ADMIN");
		Mockito.when(service.getUserByUsername(authRequest.getUsername())).thenReturn(userInfo);

		Mockito.when(jwtService.generateToken(authRequest.getUsername())).thenReturn("sample-jwt-token");
		try {
			ResponseEntity<JwtResponse> responseEntity = userController.authenticateAndGetToken(authRequest);

			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

			JwtResponse jwtResponse = responseEntity.getBody();
			assertEquals("sample-jwt-token", jwtResponse.getJwtToken());
			assertEquals("abcd@gmail.com", jwtResponse.getEmail());
			assertEquals("ROLE_ADMIN", jwtResponse.getRole());
			assertEquals("abcd", jwtResponse.getUsername());
		} catch (UsernameNotFoundException ex) {
			assertEquals("invalid user request !", ex.getMessage());
		}

	}

}

//
//@Test
//public void testAddNewUser() {
//
//	UserDTOrequest userDTOrequest = new UserDTOrequest("xyz", "xyz@gmail.com", "STUDENT");
//
//	when(service.findByUsername(userDTOrequest.getUsername())).thenReturn(null);
//	when(service.validUsername(userDTOrequest.getUsername())).thenReturn(true);
//	when(service.addUser(any(UserInfo.class))).thenReturn("User added successfully"); // Replace with your actual
//																						// success message
//
//	System.out.println(userDTOrequest);
//	ResponseEntity<String> responseEntity = userController.addNewUser(userDTOrequest);
//	// Assert
//	if (userDTOrequest.getEmail().length() == 0 || userDTOrequest.getRole().length() == 0
//			|| userDTOrequest.getUsername().length() == 0 || userDTOrequest.getEmail().isBlank()
//			|| userDTOrequest.getRole().isBlank() || userDTOrequest.getUsername().isBlank()
//			|| service.onlyContainsWithSpecialCharacter(userDTOrequest.getEmail())
//			|| service.onlyContainsWithSpecialCharacter(userDTOrequest.getUsername())) {
//		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
//	} else if (!(userDTOrequest.getEmail().contains("@") && userDTOrequest.getEmail().contains("."))) {
//		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
//	} else if (userDTOrequest.getRole().contains("STUDENT") || userDTOrequest.getRole().contains("TRAINER")) {
//		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//		assertEquals("User added successfully", responseEntity.getBody()); // Replace with your actual success
//																			// message
//	} else {
//		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
//	}
//
//	verify(service).findByUsername(userDTOrequest.getUsername());
//	verify(service).validUsername(userDTOrequest.getUsername());
//	verify(service, times(
//			userDTOrequest.getRole().contains("STUDENT") || userDTOrequest.getRole().contains("TRAINER") ? 1 : 0))
//			.addUser(any(UserInfo.class));
//
//}
