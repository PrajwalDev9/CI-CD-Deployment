package com.jwt.example.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.gen5.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jwt.example.entity.Question;
import com.jwt.example.entity.Quiz;
import com.jwt.example.repository.QuestionRepository;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

	@InjectMocks
	private QuestionService questionService;

	@Mock
	private QuestionRepository questionRepository;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddQuestion() {
		Quiz quiz = null;
		Question question = new Question(1l, "What is JSP", "Java Servr page", "Java Service Page", "Java Sevlet Page",
				"Java Secure Page", 3, quiz);
		ArrayList<Question> questions = new ArrayList<>();
		questions.add(question);
//		quiz = new Quiz(1l, "Java Basics", questions, "Java", 1, 1, 1l, true);
		quiz = new Quiz(1l, "Java Basics", questions, "Java", 1, 1, 1l);

		Mockito.when(questionRepository.save(question)).thenReturn(question);

		Question newQuestion = questionService.addQuestion(question);

		Assertions.assertThat(newQuestion).isNotNull();
		Assertions.assertThat(newQuestion).isEqualTo(question);

	}

}
