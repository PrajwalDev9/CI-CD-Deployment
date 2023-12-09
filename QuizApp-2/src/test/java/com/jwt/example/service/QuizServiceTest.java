package com.jwt.example.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
//		quiz.setStatus(true);
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
//		quiz1.setStatus(true);
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
//		quiz2.setStatus(true);
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
//		quiz.setStatus(true);
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
	public void testFindQuizByTopic() {

		Quiz quiz2 = new Quiz();
		quiz2.setId(999l);
		quiz2.setNo_of_questions(1);
		quiz2.setQuizName("Python Basics");
		quiz2.setQuizTopic("Python");
//		quiz2.setStatus(true);
		quiz2.setTotal_duration_per_question(1);
		quiz2.setTrainerId(2l);
		List<Question> questions2 = new ArrayList<>();
		questions2.add(new Question(1l, "What is PVM", "Python Virtual Machine", "Java Development Kit",
				"Java Deployment Kit", "Java Developer Kit", 1, quiz2));
		quiz2.setQuestions(questions2);

		String topic = "Python";

		Mockito.when(quizRepository.findQuizByQuizTopic(topic)).thenReturn(Optional.of(quiz2));

		Quiz resultQuiz = quizService.findQuizByTopic(topic);

		assertThat(resultQuiz).isNotNull();
		assertThat(resultQuiz.getQuizTopic()).isEqualTo(topic);

	}

	@Test
	public void testGetAllQuizOfATrainer() {

		
		Long trainerId=2l;
		
		Quiz quiz1 = new Quiz();
		quiz1.setId(1l);
		quiz1.setNo_of_questions(1);
		quiz1.setQuizName("Java Basics");
		quiz1.setQuizTopic("Java");
//		quiz1.setStatus(true);
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
//		quiz2.setStatus(true);
		quiz2.setTotal_duration_per_question(1);
		quiz2.setTrainerId(trainerId);
		List<Question> questions = new ArrayList<>();
		questions.add(new Question(1l, "What is JDK", "JAVA Kit", "Java Development Kit", "Java Deployment Kit",
				"Java Developer Kit", 2, quiz2));
		quiz2.setQuestions(questions);

		

		Mockito.when(quizRepository.getAllByTrainerId(trainerId)).thenReturn(List.of(quiz1,quiz2));
 
		
		List<Quiz> resultQuizes = quizService.getAllQuizOfATrainer(trainerId);

		
		Assertions.assertThat(resultQuizes).hasSize(2);

	}
	
	@Test
	public void testGetAllQuizOfATrainerByTopic() {
		Long trainerId=2l;
		String topic="Java";
		
		Quiz quiz1 = new Quiz();
		quiz1.setId(1l);
		quiz1.setNo_of_questions(1);
		quiz1.setQuizName("Java Basics");
		quiz1.setQuizTopic("Java");
//		quiz1.setStatus(true);
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
//		quiz2.setStatus(true);
		quiz2.setTotal_duration_per_question(1);
		quiz2.setTrainerId(trainerId);
		List<Question> questions = new ArrayList<>();
		questions.add(new Question(1l, "What is JDK", "JAVA Kit", "Java Development Kit", "Java Deployment Kit",
				"Java Developer Kit", 2, quiz2));
		quiz2.setQuestions(questions);
		
		Mockito.when(quizRepository.getAllByTrainerIdAndQuizTopic(trainerId,topic)).thenReturn(List.of(quiz1,quiz2));
		
		List<Quiz> resultQuizes=quizService.getAllQuizOfATrainerByTopic(topic, trainerId);
		
		Assertions.assertThat(resultQuizes).hasSize(2);
	}

}
