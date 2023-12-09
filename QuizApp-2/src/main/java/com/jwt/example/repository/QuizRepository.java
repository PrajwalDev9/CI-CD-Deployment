package com.jwt.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.example.entity.Quiz;



@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
	Optional<Quiz> findQuizByQuizTopic(String quizTopic);
	List<Quiz> getAllByTrainerId(Long trainerId);
	List<Quiz> getAllByTrainerIdAndQuizTopic(Long trainerId,String quizTopic);
	
}
