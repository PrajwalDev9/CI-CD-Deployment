package com.jwt.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jwt.example.entity.UserInfo;
import com.jwt.example.entity.UserInfoDetails;
import com.jwt.example.repository.UserInfoRepository;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;

public class UserInfoServiceTest {
	@InjectMocks
	private UserInfoService userInfoService;
	@Mock
	private UserInfoRepository userInfoRepository;
	@Mock
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testLoadUserByUsername_ExistingUser() {
		String username = "testUser";
		UserInfo userInfo = new UserInfo(1, username, "password", "ROLE_USER");
		Mockito.when(userInfoRepository.findByUsername(username)).thenReturn(Optional.of(userInfo));
		UserInfoDetails userDetails = (UserInfoDetails) userInfoService.loadUserByUsername(username);
		assertThat(userDetails).isNotNull();
		assertThat(userDetails.getUsername()).isEqualTo(username);
	}

	@Test
	public void testLoadUserByUsername_UserNotFound() {
		String username = "nonExistentUser";
		Mockito.when(userInfoRepository.findByUsername(username)).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername(username));
	}

	@Test
	public void testAddUser() {
		UserInfo userInfo = new UserInfo(1, "newUser", "password", "ROLE_USER");
		String encodedPassword = "encodedPassword";
		when(passwordEncoder.encode(userInfo.getPassword())).thenReturn(encodedPassword);
		when(userInfoRepository.save(userInfo)).thenReturn(userInfo);
		String result = userInfoService.addUser(userInfo);
		assertThat(result).isEqualTo("User Added Successfully");
		assertThat(userInfo.getPassword()).isEqualTo(encodedPassword);
	}

	@Test
	public void testGetUserByUsername() {
		String username = "testUser";
		UserInfo userInfo = new UserInfo(1, username, "password", "ROLE_USER");
		when(userInfoRepository.findByUsername(username)).thenReturn(Optional.of(userInfo));
		UserInfo retrievedUser = userInfoService.getUserByUsername(username);
		assertThat(retrievedUser).isNotNull();
		assertThat(retrievedUser.getUsername()).isEqualTo(username);
	}

	@Test
	public void testFindIdByUsername_UserExists() {
		String username = "testUser";
		UserInfo userInfo = new UserInfo(1, username, "password", "ROLE_USER");
		when(userInfoRepository.findByUsername(username)).thenReturn(Optional.of(userInfo));
		int userId = userInfoService.findIdByUsername(username);
		assertThat(userId).isEqualTo(1);
	}

	@Test
	public void testFindIdByUsername_UserNotExists() {
		String username = "nonExistentUser";
		when(userInfoRepository.findByUsername(username)).thenReturn(Optional.empty());
		UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class,
				() -> userInfoService.loadUserByUsername(username));
		assertThat(usernameNotFoundException.getMessage()).isEqualTo("User not found " + username);
	}
}