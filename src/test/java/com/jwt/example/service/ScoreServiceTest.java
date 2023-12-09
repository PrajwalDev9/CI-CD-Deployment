package com.jwt.example.service;

import org.junit.gen5.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jwt.example.entity.Score;
import com.jwt.example.repository.ScoreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ScoreServiceTest {

	@InjectMocks
	private ScoreService scoreService;

	@Mock
	private ScoreRepository scoreRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testAddScore() {

		Score inputScore = new Score(2l, "Java", 20, "Puja", 9, 300);
		when(scoreRepository.save(any(Score.class))).thenReturn(inputScore);

		Score resultScore = scoreService.addScore(inputScore);

		assertEquals(inputScore, resultScore);

	}

	@Test
	void testGetLeaderBoardByQuizId() {

		long quizId = 123;
		List<Score> mockScores = new ArrayList<>();

		when(scoreRepository.findByQuizId(quizId)).thenReturn(mockScores);

		List<Score> resultLeaderboard = scoreService.getLeaderBoardByQuizId(quizId);

		assertNotNull(resultLeaderboard);
		assertEquals(mockScores, resultLeaderboard);

	}

	@Test
	void testGetLeaderboard() {
		List<Score> mockScores = new ArrayList<>();

		when(scoreRepository.findAll()).thenReturn(mockScores);

		List<Score> resultLeaderboard = scoreService.getLeaderboard();

		assertNotNull(resultLeaderboard);
		assertEquals(mockScores, resultLeaderboard);

	}

	@Test
	void testCanAddWithExistingScore() {
		// Arrange
		Score inputScore = new Score();
		Score existingScore = new Score();

		// Mock repository behavior
		when(scoreRepository.findByQuizIdAndQuizTopicAndStudentIdAndUsernameAndMarksAndTotalTimeDuration(
				inputScore.getQuizId(), inputScore.getQuizTopic(), inputScore.getStudentId(), inputScore.getUsername(),
				inputScore.getMarks(), inputScore.getTotalTimeDuration())).thenReturn(existingScore);

		// Act
		Long result = scoreService.canAdd(inputScore);

		// Assert
		assertEquals(existingScore.getId(), result);
	}

	@Test
	void testGetById() {

		Long id = 1L;
		Score expectedScore = new Score(/* provide necessary data */);

		when(scoreRepository.findById(id)).thenReturn(Optional.of(expectedScore));

		Score result = scoreService.getById(id);

		assertNotNull(result);
		assertEquals(expectedScore, result);
	}

	@Test
	void testGetByIdNotFound() {
		// Arrange
		Long id = 1L;

		// Mock repository behavior for a case where the score is not found
		when(scoreRepository.findById(id)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(NoSuchElementException.class, () -> scoreService.getById(id));
	}
}
