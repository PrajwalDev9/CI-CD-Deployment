package com.jwt.example.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

	public List<Quiz> findQuizByTopic(String topic) {
		return quizRepository.findQuizByQuizTopic(topic);
	}
	
	
	

	public List<Quiz> getAllQuizOfATrainer(Long trainerId) {
		return quizRepository.getAllByTrainerId(trainerId);
	}

	public List<Quiz> getAllQuizOfATrainerByTopic(String topic, Long trainerId) {
		return quizRepository.getAllByTrainerIdAndQuizTopic(trainerId, topic);
	}


	


	public Quiz getById(Long id) {
		// TODO Auto-generated method stub
		if (id != 0) {
			return quizRepository.findById(id).get();
		} else {
			return null;
		}

	}

	public Set<String> findAllTopics() 
	{
		List<Quiz> quizs=getAllQuiz();
		Set<String> topics=new HashSet<>();
		for(Quiz quiz:quizs) {
			topics.add(quiz.getQuizTopic());
		}
		return topics;
	}

	public boolean findByQuizNameAndTrainerId(String quizName,Long trainerId) {

			if(quizRepository.findByQuizNameAndTrainerId(quizName,trainerId) == null) {
				return false;
			}
	
		return true;
	}


}
