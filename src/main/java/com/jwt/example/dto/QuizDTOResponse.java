package com.jwt.example.dto;

import java.util.ArrayList;
import java.util.List;

import com.jwt.example.entity.Quiz;

public class QuizDTOResponse {
	private Long quizId;
	private String quizName;
	private String quizTopic;
	private int totalQuestions;
	private int quizDuration;
	private String trainerName;


	public static QuizDTOResponse toDTO(Quiz quiz, String trainerName) {
		QuizDTOResponse dtoResponse = new QuizDTOResponse();
		dtoResponse.setQuizId(quiz.getId());
		dtoResponse.setQuizTopic(quiz.getQuizTopic());
		dtoResponse.setQuizName(quiz.getQuizName());
		dtoResponse.setQuizDuration(quiz.getTotal_duration_per_question() * quiz.getNo_of_questions());
		dtoResponse.setTrainerName(trainerName);
		dtoResponse.setTotalQuestions(quiz.getNo_of_questions());
		return dtoResponse;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public int getQuizDuration() {
		return quizDuration;
	}

	public void setQuizDuration(int quizDuration) {
		this.quizDuration = quizDuration;
	}

	public QuizDTOResponse() {
		super();
	}

	public QuizDTOResponse(Long quizId, String quizName, String quizTopic, int totalQuestions, int quizDuration,
			Long trainerId) {
		super();
		this.quizId = quizId;
		this.quizName = quizName;

		this.quizTopic = quizTopic;
		this.totalQuestions = totalQuestions;
		this.quizDuration = quizDuration;

	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public String getQuizTopic() {
		return quizTopic;
	}

	public void setQuizTopic(String quizTopic) {
		this.quizTopic = quizTopic;
	}



}
