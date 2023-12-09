package com.jwt.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.example.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
	List<Score> findByQuizTopic(String quizTopic);

	List<Score> findByQuizTopicAndQuizId(String quizTopic, long quizId);

	List<Score> findByQuizId(long quizID);

	Score findByQuizIdAndQuizTopicAndStudentIdAndUsernameAndMarksAndTotalTimeDuration(Long quizId, String quizTopic,
			long studentId, String username, int marks, int totalTimeDuration);

}
