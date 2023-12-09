package com.jwt.example.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.gen5.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jwt.example.entity.Question;
import com.jwt.example.entity.Quiz;
import com.jwt.example.repository.QuizRepository;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

	@InjectMocks
	private QuizService quizService;

	@Mock
	private QuizRepository quizRepository;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testAddQuiz() {
		Quiz quiz = new Quiz();
		quiz.setId(999l);
		quiz.setNo_of_questions(1);
		quiz.setQuizName("Java Basics");
		quiz.setQuizTopic("Java");

		quiz.setTotal_duration_per_question(1);
		quiz.setTrainerId(2l);
		List<Question> questions = new ArrayList<>();
		questions.add(new Question(1l, "What is JDK", "JAVA Kit", "Java Development Kit", "Java Deployment Kit",
				"Java Developer Kit", 2, quiz));
		quiz.setQuestions(questions);

		when(quizRepository.save(Mockito.any(Quiz.class))).thenReturn(quiz);
		Quiz savedQuiz = quizService.addQuiz(quiz);
		Assertions.assertThat(savedQuiz).isNotNull();

	}

	@Test
	public void testGetAllQuiz() {
		Quiz quiz1 = new Quiz();
		quiz1.setId(999l);
		quiz1.setNo_of_questions(1);
		quiz1.setQuizName("Java Basics");
		quiz1.setQuizTopic("Java");
		quiz1.setTotal_duration_per_question(1);
		quiz1.setTrainerId(2l);

		List<Question> questions = new ArrayList<>();
		questions.add(new Question(1l, "What is JDK", "JAVA Kit", "Java Development Kit", "Java Deployment Kit",
				"Java Developer Kit", 2, quiz1));
		quiz1.setQuestions(questions);

		Quiz quiz2 = new Quiz();
		quiz2.setId(999l);
		quiz2.setNo_of_questions(1);
		quiz2.setQuizName("Python Basics");
		quiz2.setQuizTopic("Python");
		quiz2.setTotal_duration_per_question(1);
		quiz2.setTrainerId(2l);
		List<Question> questions2 = new ArrayList<>();
		questions2.add(new Question(1l, "What is PVM", "Python Virtual Machine", "Java Development Kit",
				"Java Deployment Kit", "Java Developer Kit", 1, quiz2));
		quiz2.setQuestions(questions2);

		List<Quiz> quizs = new ArrayList<>();
		quizs.add(quiz1);
		quizs.add(quiz2);
		;
		when(quizRepository.findAll()).thenReturn(quizs);

		List<Quiz> allQuizes = quizService.getAllQuiz();

		assertThat(allQuizes).isNotNull();
		assertThat(allQuizes).hasSize(2);

		assertThat(allQuizes).containsExactlyInAnyOrder(quiz1, quiz2);

	}

	@Test
	public void testFindQuizById() {
		Long quizId = 1L;

		Quiz quiz = new Quiz();
		quiz.setId(1l);
		quiz.setNo_of_questions(1);
		quiz.setQuizName("Java Basics");
		quiz.setQuizTopic("Java");
		quiz.setTotal_duration_per_question(1);
		quiz.setTrainerId(2l);

		List<Question> questions = new ArrayList<>();
		questions.add(new Question(1l, "What is JDK", "JAVA Kit", "Java Development Kit", "Java Deployment Kit",
				"Java Developer Kit", 2, quiz));
		quiz.setQuestions(questions);

		Mockito.when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));

		Quiz result = quizService.findQuizById(quizId);

		Assertions.assertThat(result.getId()).isEqualTo(1l);
		Assertions.assertThat(result.getId()).isLessThan(2l);
	}

	@Test
	void testFindQuizByTopic() {

		String topic = "Python";
		List<Quiz> expectedQuizList = new ArrayList<>();

		when(quizRepository.findQuizByQuizTopic(topic)).thenReturn(expectedQuizList);

		List<Quiz> resultQuizList = quizService.findQuizByTopic(topic);

		assertEquals(expectedQuizList.size(), resultQuizList.size());

	}

	@Test
	public void testGetAllQuizOfATrainer() {

		Long trainerId = 2l;

		Quiz quiz1 = new Quiz();
		quiz1.setId(1l);
		quiz1.setNo_of_questions(1);
		quiz1.setQuizName("Java Basics");
		quiz1.setQuizTopic("Java");
		quiz1.setTotal_duration_per_question(1);
		quiz1.setTrainerId(trainerId);
		List<Question> questions1 = new ArrayList<>();
		questions1.add(new Question(1l, "What is JDK", "JAVA Kit", "Java Development Kit", "Java Deployment Kit",
				"Java Developer Kit", 2, quiz1));
		quiz1.setQuestions(questions1);

		Quiz quiz2 = new Quiz();
		quiz2.setId(1l);
		quiz2.setNo_of_questions(1);
		quiz2.setQuizName("Java Basics");
		quiz2.setQuizTopic("Java");
		quiz2.setTotal_duration_per_question(1);
		quiz2.setTrainerId(trainerId);
		List<Question> questions = new ArrayList<>();
		questions.add(new Question(1l, "What is JDK", "JAVA Kit", "Java Development Kit", "Java Deployment Kit",
				"Java Developer Kit", 2, quiz2));
		quiz2.setQuestions(questions);

		Mockito.when(quizRepository.getAllByTrainerId(trainerId)).thenReturn(List.of(quiz1, quiz2));

		List<Quiz> resultQuizes = quizService.getAllQuizOfATrainer(trainerId);

		Assertions.assertThat(resultQuizes).hasSize(2);

	}

	@Test
	public void testGetAllQuizOfATrainerByTopic() {
		Long trainerId = 2l;
		String topic = "Java";

		Quiz quiz1 = new Quiz();
		quiz1.setId(1l);
		quiz1.setNo_of_questions(1);
		quiz1.setQuizName("Java Basics");
		quiz1.setQuizTopic("Java");
		quiz1.setTotal_duration_per_question(1);
		quiz1.setTrainerId(trainerId);
		List<Question> questions1 = new ArrayList<>();
		questions1.add(new Question(1l, "What is JDK", "JAVA Kit", "Java Development Kit", "Java Deployment Kit",
				"Java Developer Kit", 2, quiz1));
		quiz1.setQuestions(questions1);

		Quiz quiz2 = new Quiz();
		quiz2.setId(1l);
		quiz2.setNo_of_questions(1);
		quiz2.setQuizName("Java Basics");
		quiz2.setQuizTopic("Java");
		quiz2.setTotal_duration_per_question(1);
		quiz2.setTrainerId(trainerId);
		List<Question> questions = new ArrayList<>();
		questions.add(new Question(1l, "What is JDK", "JAVA Kit", "Java Development Kit", "Java Deployment Kit",
				"Java Developer Kit", 2, quiz2));
		quiz2.setQuestions(questions);

		Mockito.when(quizRepository.getAllByTrainerIdAndQuizTopic(trainerId, topic)).thenReturn(List.of(quiz1, quiz2));

		List<Quiz> resultQuizes = quizService.getAllQuizOfATrainerByTopic(topic, trainerId);

		Assertions.assertThat(resultQuizes).hasSize(2);
	}
	@Test
    void testGetById() {
        // Arrange
        Long validId = 1L;
        Long invalidId = 0L;
 
        Quiz validQuiz = new Quiz(); // Create a valid Quiz instance for testing
 
        // Mocking repository behavior
        when(quizRepository.findById(validId)).thenReturn(Optional.of(validQuiz));
 
        // Act
        Quiz retrievedValidQuiz = quizService.getById(validId);
        Quiz retrievedInvalidQuiz = quizService.getById(invalidId);

        // Assert
        assertNotNull(retrievedValidQuiz);
        assertEquals(validQuiz, retrievedValidQuiz);
 
        assertNull(retrievedInvalidQuiz);
    }
	@Test
    void testFindAllTopics() {
        // Arrange
		
		Quiz quiz1 = new Quiz();
		quiz1.setId(999l);
		quiz1.setNo_of_questions(1);
		quiz1.setQuizName("Java Basics");
		quiz1.setQuizTopic("Java");
		quiz1.setTotal_duration_per_question(1);
		quiz1.setTrainerId(2l);

		List<Question> questions = new ArrayList<>();
		questions.add(new Question(1l, "What is JDK", "JAVA Kit", "Java Development Kit", "Java Deployment Kit",
				"Java Developer Kit", 2, quiz1));
		quiz1.setQuestions(questions);

		Quiz quiz2 = new Quiz();
		quiz2.setId(999l);
		quiz2.setNo_of_questions(1);
		quiz2.setQuizName("Python Basics");
		quiz2.setQuizTopic("Python");
		quiz2.setTotal_duration_per_question(1);
		quiz2.setTrainerId(2l);
		List<Question> questions2 = new ArrayList<>();
		questions2.add(new Question(1l, "What is PVM", "Python Virtual Machine", "Java Development Kit",
				"Java Deployment Kit", "Java Developer Kit", 1, quiz2));
		quiz2.setQuestions(questions2);
		
		
		Quiz quiz3 = new Quiz();
		quiz3.setId(999l);
		quiz3.setNo_of_questions(1);
		quiz3.setQuizName("Java Basics");
		quiz3.setQuizTopic("Java");
		quiz3.setTotal_duration_per_question(1);
		quiz3.setTrainerId(2l);

		List<Question> questions3 = new ArrayList<>();
		questions3.add(new Question(1l, "What is JDK", "JAVA Kit", "Java Development Kit", "Java Deployment Kit",
				"Java Developer Kit", 2, quiz3));
		quiz3.setQuestions(questions3);

        List<Quiz> mockQuizzes = new ArrayList<>();
        mockQuizzes.add(quiz1) ;
        mockQuizzes.add(quiz2) ;
        mockQuizzes.add(quiz3) ;
      
 
        // Mocking repository behavior
        when(quizRepository.findAll()).thenReturn(mockQuizzes);
 
        // Act
        Set<String> topics = quizService.findAllTopics();
 
        // Assert
        assertNotNull(topics);
        assertEquals(2, topics.size()); // Assuming there are two unique topics in the mocked quizzes
        assertTrue(topics.contains("Java"));
        assertTrue(topics.contains("Python"));
    }
	
	@Test
    void testFindByQuizNameAndTrainerId() {
        // Arrange
        String quizName = "SampleQuiz";
        Long trainerId = 1L;
 
        // Mocking repository behavior
        when(quizRepository.findByQuizNameAndTrainerId(quizName, trainerId)).thenReturn(null); // Assuming null means not found
 
        // Act
        boolean result = quizService.findByQuizNameAndTrainerId(quizName, trainerId);
 
        // Assert
        assertFalse(result); // Expecting false since the repository returned null
    }
 
    @Test
    void testFindByQuizNameAndTrainerIdFound() {
        // Arrange
        String quizName = "ExistingQuiz";
        Long trainerId = 1L;
 
        // Mocking repository behavior
        when(quizRepository.findByQuizNameAndTrainerId(quizName, trainerId)).thenReturn(new Quiz(/* quiz details */));
 
        // Act
        boolean result = quizService.findByQuizNameAndTrainerId(quizName, trainerId);
 
        // Assert
        assertTrue(result); // Expecting true since the repository found a quiz
    }
}

