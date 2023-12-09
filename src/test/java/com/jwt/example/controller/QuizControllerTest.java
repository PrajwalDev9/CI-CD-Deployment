package com.jwt.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;

import com.jwt.example.dto.AnswerDTO;
import com.jwt.example.dto.LeaderBoard;
import com.jwt.example.dto.QuestionDTO;
import com.jwt.example.dto.QuestionPaper;
import com.jwt.example.dto.QuizDTO;
import com.jwt.example.dto.QuizDTOResponse;
import com.jwt.example.dto.QuizStudentResponse;

import com.jwt.example.entity.Quiz;
import com.jwt.example.entity.Score;
import com.jwt.example.entity.UserInfo;
import com.jwt.example.service.QuestionService;
import com.jwt.example.service.QuizService;

import com.jwt.example.service.ScoreService;
import com.jwt.example.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
	private ScoreService scoreService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateQuizValidData() {
	
		Principal principal = () -> "testUser";

		List<QuestionDTO> questionDTO = new ArrayList<>();

		QuestionDTO dto = new QuestionDTO(1l, "What is JSP", "Java Servr page", "Java Service Page", "Java Sevlet Page",
				"Java Secure Page", 3);
		questionDTO.add(dto);
		QuizDTO quizDto = new QuizDTO(1l, questionDTO, "Java", "Java Basics", 1, 1, 1l);


		Quiz expectedQuiz = QuizDTO.fromDTO(quizDto, 1l);

		Mockito.when(userService.findIdByUsername(principal.getName())).thenReturn((int) 1l);

		Mockito.when(quizService.addQuiz(expectedQuiz)).thenReturn(expectedQuiz);

		ResponseEntity<QuizDTO> responseEntity = quizController.createQuiz(quizDto, principal);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	public void testGetAllQuiz() {
		
		Principal principal = () -> "testUser";

		List<QuestionDTO> questionDTO1 = new ArrayList<>();

		QuestionDTO dto1 = new QuestionDTO(1l, "What is JSP", "Java Servr page", "Java Service Page",
				"Java Sevlet Page", "Java Secure Page", 3);
		questionDTO1.add(dto1);
		
		QuizDTO quizDto1 = new QuizDTO(1l, questionDTO1, "Java", "Java Basics", 1, 1, 1l);

		List<QuestionDTO> questionDTO2 = new ArrayList<>();
		
		QuestionDTO dto2 = new QuestionDTO(1l, "What is JVM", "Java Virtual Machine", "Java Virtual Message",
				"JSP Virtual Message", "NONE", 1);
		  questionDTO2.add(dto2);
		  
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
		
		Principal principal = () -> "testUser";

		List<QuestionDTO> questionDTO1 = new ArrayList<>();

		QuestionDTO dto1 = new QuestionDTO(1l, "What is JSP", "Java Servr page", "Java Service Page",
				"Java Sevlet Page", "Java Secure Page", 3);
		questionDTO1.add(dto1);
		
		QuizDTO quizDto1 = new QuizDTO(1l, questionDTO1, "Java", "Java Basics", 1, 1, 1l);

		List<QuestionDTO> questionDTO2 = new ArrayList<>();
		
		QuestionDTO dto2 = new QuestionDTO(1l, "What is JVM", "Java Virtual Machine", "Java Virtual Message",
				"JSP Virtual Message", "NONE", 1);
		  questionDTO2.add(dto2);
		  
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

	@Test
    void testGetAllQuizForStudent() {
        // Arrange
        List<Quiz> mockQuizList = Arrays.asList(new Quiz(), new Quiz()); // Mock data for quizService.getAllQuiz()
 
        // Mocking quizService behavior
        when(quizService.getAllQuiz()).thenReturn(mockQuizList);
 
        // Act
        ResponseEntity<List<QuizStudentResponse>> responseEntity = quizController.getAllQuiz();
 
        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
 
        List<QuizStudentResponse> quizResponses = responseEntity.getBody();
        assertNotNull(quizResponses);
        assertEquals(mockQuizList.size(), quizResponses.size());
 
        // You might want to add more specific assertions based on your application logic.
 
        // Verifying that quizService.getAllQuiz() was called
        Mockito. verify(quizService).getAllQuiz();
    }
	
    @Test
    void testAttemptQuiz() {
        // Arrange
        Long quizId = 1L;
        Quiz mockQuiz = new Quiz(); // Mock data for quizService.findQuizById()
        when(quizService.findQuizById(quizId)).thenReturn(mockQuiz);
 
        // Act
        ResponseEntity<QuestionPaper> responseEntity = quizController.attemptQuiz(quizId);
 
        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
 
        QuestionPaper questionPaper = responseEntity.getBody();
        assertNotNull(questionPaper);
 
        // You might want to add more specific assertions based on your application logic.
 
        // Verifying that quizService.findQuizById() was called with the correct argument
         Mockito. verify(quizService).findQuizById(quizId);
    }
    
  
        @Test
        void testCalculateResult() {
            // Arrange
            AnswerDTO answerDTO = new AnswerDTO(); 
            answerDTO.setQuizId(1L);
            answerDTO.setTotalTimeDuration(300);
            ArrayList<Integer> list = new ArrayList<>();
           
            list.add(1);
            list.add(1);
            list.add(3);
            list.add(2);
            list.add(2);
            list.add(1);
            list.add(1);
            list.add(2);
            list.add(1);
            list.add(3);
            answerDTO.setSelectedOption(list);
            
            Principal mockPrincipal = mock(Principal.class);
            when(mockPrincipal.getName()).thenReturn("Raju");
            when(scoreService.canAdd(any(Score.class))).thenReturn((long) 0);
            when(scoreService.addScore(any(Score.class))).thenReturn(new Score());
            Quiz mockQuiz = new Quiz(); 
            List<QuestionDTO> questionDTO = new ArrayList<>();
            QuizDTO quizDto = new QuizDTO(1l, questionDTO, "Java", "Java Basics", 1, 1, 26L);            
            mockQuiz = QuizDTO.fromDTO(quizDto, (long) 26);
            
            
            when(quizService.findQuizById(answerDTO.getQuizId())).thenReturn(mockQuiz);
     
            UserInfo mockUserInfo = new UserInfo("Raju","raju@gmail.com","raju@972","ROLE_STUDENT"); // Mock data for userService.getUserById()
            
            when(userService.getUserById(anyLong())).thenReturn(Optional.of(mockUserInfo));
     
      
            Score mockScore = new Score(); // Mock data for scoreService.addScore()
            when(scoreService.addScore(any(Score.class))).thenReturn(mockScore);
           
           
            ResponseEntity<Score> responseEntity = quizController.calculateResult(answerDTO, mockPrincipal);
          
 
            Score score = responseEntity.getBody();
            assertNotNull(score);
     
            verify(quizService).findQuizById(answerDTO.getQuizId());
        
        }
    
    
//    @Test
//    void testAllLeaderBoard() {
//     
//        // Mock data
//        Set<String> mockTopics = new HashSet<>(Arrays.asList("java-1", "java-2", "java-3")); 
//        when(quizService.findAllTopics()).thenReturn(mockTopics);
// 
//        // Mock user data
//    	Principal principal = () -> "testUser";
//
//		List<QuestionDTO> questionDTO1 = new ArrayList<>();
//
//		QuestionDTO dto1 = new QuestionDTO(1l, "What is JSP", "Java Servr page", "Java Service Page",
//				"Java Sevlet Page", "Java Secure Page", 3);
//		questionDTO1.add(dto1);
//		
//		QuizDTO quizDto1 = new QuizDTO(1l, questionDTO1, "Java", "Java Basics", 1, 1, 1l);
//
//		List<QuestionDTO> questionDTO2 = new ArrayList<>();
//		
//		QuestionDTO dto2 = new QuestionDTO(1l, "What is JVM", "Java Virtual Machine", "Java Virtual Message",
//				"JSP Virtual Message", "NONE", 1);
//		  questionDTO2.add(dto2);
//		  
//		QuizDTO quizDto2 = new QuizDTO(1l, questionDTO2, "Java", "Java Basics", 1, 1, 1l);
//
//      
//		Quiz quiz1 = QuizDTO.fromDTO(quizDto1, (long) 11);
//		Quiz quiz2 = QuizDTO.fromDTO(quizDto2, (long) 11);
//		
//        when(quizService.findQuizByTopic("java-1")).thenReturn(Arrays.asList(quiz1));
//        when(quizService.findQuizByTopic("java-2")).thenReturn(Arrays.asList(quiz2));
//        when(quizService.findQuizByTopic("java-3")).thenReturn(Collections.emptyList());
// 
//        // Mock score data
//        Score mockScore1 = new Score(1l,"Java Basics",20,"Priya",10,500); // Mock data for scoreService.getLeaderBoardByQuizId()
//        
//        Score mockScore2 =  new Score(1l,"Java Basics",20,"Priyanka",10,200);
//        when(scoreService.getLeaderBoardByQuizId(quiz1.getId())).thenReturn(Arrays.asList(mockScore1, mockScore2));
//        when(scoreService.getLeaderBoardByQuizId(quiz2.getId())).thenReturn(Collections.emptyList());
// 
//        // Act
//        List<LeaderBoard> leaderBoards = quizController.allLeaderBoard();
// 
//        // Assert
//        assertNotNull(leaderBoards);
//        verify(quizService, times(3)).findQuizByTopic(anyString());
//        verify(scoreService, times(2)).getLeaderBoardByQuizId(anyLong());
//    }
}

