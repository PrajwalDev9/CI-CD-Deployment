package com.jwt.example.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.example.entity.Quiz;
import com.jwt.example.repository.QuizRepository;

import jakarta.transaction.Transactional;

@Service
public class QuizService {
	@Autowired
	private QuizRepository quizRepository;

	public Quiz addQuiz(Quiz quiz) {
		return quizRepository.save(quiz);
	}

	public List<Quiz> getAllQuiz() {
		return quizRepository.findAll();
	}

	public Quiz findQuizById(Long id) {
		return quizRepository.findById(id).get();
	}

	public Quiz findQuizByTopic(String topic) {
		return quizRepository.findQuizByQuizTopic(topic).get();
	}

	public List<Quiz> getAllQuizOfATrainer(Long trainerId) {
		return quizRepository.getAllByTrainerId(trainerId);
	}

	public List<Quiz> getAllQuizOfATrainerByTopic(String topic, Long trainerId) {
		return quizRepository.getAllByTrainerIdAndQuizTopic(trainerId, topic);
	}
}
