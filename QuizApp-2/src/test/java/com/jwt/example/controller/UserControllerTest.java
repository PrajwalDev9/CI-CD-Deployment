package com.jwt.example.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

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
		
		
		UserDTOrequest userDTOrequest = new UserDTOrequest("abc","abc@gmail.com","ROLE_TRAINER");
		UserInfo user = UserDTOrequest.fromDTo(userDTOrequest);
		Mockito.when(service.addUser(user)).thenReturn("User Added Successfully");
		
		ResponseEntity<String> resultString = userController.addNewUser(userDTOrequest);

		assertThat(resultString.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
	}
      

    
    @Test
    public void testAuthenticateAndGetToken() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("abcd");
        authRequest.setPassword("abc@123");
 
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
      Mockito.when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
 
        UserInfo userInfo = new UserInfo("abcd","abcd@gmail.com",
    			"abc@123","ROLE_ADMIN");
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
        }
        catch(UsernameNotFoundException ex) {
        	 assertEquals("invalid user request !", ex.getMessage());
        }
        
    }


	}









//@Test
//public void testAuthenticateAndGetToken() {
//  AuthRequest authRequest = new AuthRequest();
//  authRequest.setUsername("Anant");
//  authRequest.setPassword("Anant@278");
//
//
//long id=   service.findIdByUsername("Anant");
//System.out.println(id);
//  Authentication authentication = new UsernamePasswordAuthenticationToken( authRequest.getUsername(), authRequest .getPassword());
////  Mockito.when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
//  Mockito.when(authenticationManager.authenticate(any())).thenReturn(authentication);
//
//System.out.println(authentication);
//  	   UserInfo userInfo = new UserInfo("Anant","Anant@gmail.com",
//        			"Anant@278","ROLE_TRAINER");
//             Mockito. when(service.getUserByUsername(authRequest.getUsername())).thenReturn(userInfo);
//      
//             Mockito. when(jwtService.generateToken(authRequest.getUsername())).thenReturn("sample-jwt-token");
//      
//             ResponseEntity<JwtResponse> responseEntity = userController.authenticateAndGetToken(authRequest);
//      
//             assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//}

