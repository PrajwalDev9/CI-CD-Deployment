package com.jwt.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;

import com.jwt.example.dto.QuestionDTO;
import com.jwt.example.dto.QuizDTO;
import com.jwt.example.dto.QuizDTOResponse;
import com.jwt.example.entity.Quiz;
import com.jwt.example.service.QuestionService;
import com.jwt.example.service.QuizService;
import com.jwt.example.service.QuizStatusService;
import com.jwt.example.service.UserInfoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class QuizControllerTest {
	@InjectMocks
	private QuizController quizController;
	@Mock
	private QuizService quizService;
	@Mock
	private QuestionService questionService;
	@Mock
	private UserInfoService userService;
	@Mock
	private QuizStatusService quizStatusService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateQuiz() {
		// Implement test for createQuiz endpoint
		Principal principal = () -> "testUser";

		List<QuestionDTO> questionDTO = new ArrayList<>();

		QuestionDTO dto = new QuestionDTO(1l, "What is JSP", "Java Servr page", "Java Service Page", "Java Sevlet Page",
				"Java Secure Page", 3);
		questionDTO.add(dto);
//		QuizDTO quizDto = new QuizDTO(1l, questionDTO, "Java", "Java Basics", 1, 1, 1l, true);
		QuizDTO quizDto = new QuizDTO(1l, questionDTO, "Java", "Java Basics", 1, 1, 1l);


		Quiz expectedQuiz = QuizDTO.fromDTO(quizDto, 1l);

		Mockito.when(userService.findIdByUsername(principal.getName())).thenReturn((int) 1l);

		Mockito.when(quizService.addQuiz(expectedQuiz)).thenReturn(expectedQuiz);

		ResponseEntity<QuizDTO> responseEntity = quizController.createQuiz(quizDto, principal);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

//	   assertThat(responseEntity.getBody()).isEqualTo(expectedQuiz);

	}

	@Test
	public void testGetAllQuiz() {
		// Implement test for getAllQuiz endpoint

		
		Principal principal = () -> "testUser";

		List<QuestionDTO> questionDTO1 = new ArrayList<>();

		QuestionDTO dto1 = new QuestionDTO(1l, "What is JSP", "Java Servr page", "Java Service Page",
				"Java Sevlet Page", "Java Secure Page", 3);
		questionDTO1.add(dto1);
		
//		QuizDTO quizDto1 = new QuizDTO(1l, questionDTO1, "Java", "Java Basics", 1, 1, 1l, true);
		QuizDTO quizDto1 = new QuizDTO(1l, questionDTO1, "Java", "Java Basics", 1, 1, 1l);

		List<QuestionDTO> questionDTO2 = new ArrayList<>();
		
		QuestionDTO dto2 = new QuestionDTO(1l, "What is JVM", "Java Virtual Machine", "Java Virtual Message",
				"JSP Virtual Message", "NONE", 1);
		  questionDTO2.add(dto2);
		  
//		QuizDTO quizDto2 = new QuizDTO(1l, questionDTO2, "Java", "Java Basics", 1, 1, 1l, true);
		QuizDTO quizDto2 = new QuizDTO(1l, questionDTO2, "Java", "Java Basics", 1, 1, 1l);

      
        
		Quiz quiz1 = QuizDTO.fromDTO(quizDto1, (long) 11);
		Quiz quiz2 = QuizDTO.fromDTO(quizDto2, (long) 11);
		
		
		List<Quiz> quizs = new ArrayList<>();
		quizs.add(quiz1);
		quizs.add(quiz2);
		
		
		Mockito.when(userService.findIdByUsername(principal.getName())).thenReturn((int) 1l);
		
		Mockito.when(quizService.getAllQuizOfATrainer((long) 11)).thenReturn(quizs);

		ResponseEntity<List<QuizDTOResponse>> responseEntity = quizController.getAllQuiz(principal);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@Test
	public void testFindQuizByTopicOfTrainer() {
		// Implement test for findQuizByTopicOfTrainer endpoint
		Principal principal = () -> "testUser";

		List<QuestionDTO> questionDTO1 = new ArrayList<>();

		QuestionDTO dto1 = new QuestionDTO(1l, "What is JSP", "Java Servr page", "Java Service Page",
				"Java Sevlet Page", "Java Secure Page", 3);
		questionDTO1.add(dto1);
		
//		QuizDTO quizDto1 = new QuizDTO(1l, questionDTO1, "Java", "Java Basics", 1, 1, 1l, true);
		QuizDTO quizDto1 = new QuizDTO(1l, questionDTO1, "Java", "Java Basics", 1, 1, 1l);

		List<QuestionDTO> questionDTO2 = new ArrayList<>();
		
		QuestionDTO dto2 = new QuestionDTO(1l, "What is JVM", "Java Virtual Machine", "Java Virtual Message",
				"JSP Virtual Message", "NONE", 1);
		  questionDTO2.add(dto2);
		  
//		QuizDTO quizDto2 = new QuizDTO(1l, questionDTO2, "Java", "Java Basics", 1, 1, 1l, true);
		QuizDTO quizDto2 = new QuizDTO(1l, questionDTO2, "Java", "Java Basics", 1, 1, 1l);

      
		Quiz quiz1 = QuizDTO.fromDTO(quizDto1, (long) 11);
		Quiz quiz2 = QuizDTO.fromDTO(quizDto2, (long) 11);
		

		List<Quiz> quizs = new ArrayList<>();
		quizs.add(quiz1);
		quizs.add(quiz2);
		
		
	    Mockito.when(userService.findIdByUsername(principal.getName())).thenReturn((int) 1l);
		
		Mockito.when(quizService.getAllQuizOfATrainerByTopic("Java",(long) 11)).thenReturn(quizs);

		ResponseEntity<List<QuizDTOResponse>> responseEntity = quizController.findQuizByTopicOfTrainer("Java",principal);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

	}




}