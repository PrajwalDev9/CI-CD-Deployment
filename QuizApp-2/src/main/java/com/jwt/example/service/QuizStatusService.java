package com.jwt.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.example.entity.Quiz;
import com.jwt.example.entity.QuizStatus;
import com.jwt.example.repository.QuizRepository;
import com.jwt.example.repository.QuizStatusRepository;

import io.swagger.v3.oas.annotations.servers.Server;

@Service
public class QuizStatusService {

	@Autowired
	private QuizStatusRepository quizStatusRepository;

	@Autowired
	private QuizService quizService;

	public QuizStatus deleteStatus(Long statusId) {
		QuizStatus quizStatus = quizStatusRepository.findById(statusId).get();
		if (quizStatus != null) {
			quizStatusRepository.deleteById(statusId);
			return quizStatus;
		}
		return null;
	}

	public QuizStatus addStatus(QuizStatus quizStatus) {
		return quizStatusRepository.save(quizStatus);
	}

	public List<QuizStatus> getAllStatus() {
		return quizStatusRepository.findAll();
	}

//	public String updateQuizStatus(Long statusId) {
//		QuizStatus quizStatus = quizStatusRepository.findById(statusId).get();
//		Long quizId = quizStatus.getQuizId();
//		Quiz quiz = quizService.findQuizById(quizId);
//		quiz.setStatus(!quiz.getStatus());
//		quizService.addQuiz(quiz);
//		deleteStatus(statusId);
//		return "Done";
//
//	}

}
