package com.jwt.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.example.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
	List<Score> findByQuizTopic(String quizTopic);

	List<Score> findByQuizTopicAndQuizId(String quizTopic, long quizId);
}
