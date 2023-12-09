package com.jwt.example.dto;

import java.util.ArrayList;
import java.util.List;

import com.jwt.example.entity.Question;
import com.jwt.example.entity.Quiz;

import lombok.Data;


public class QuizStudentResponse {

	private Long id;
	private List<QuestionPaperDTO> questions;
	private String quizTopic;
	private String quizName;
	private int no_of_questions;
	private int quizDuration;
	private Long trainerId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getQuizTopic() {
		return quizTopic;
	}
	public void setQuizTopic(String quizTopic) {
		this.quizTopic = quizTopic;
	}
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	public int getNo_of_questions() {
		return no_of_questions;
	}
	public void setNo_of_questions(int no_of_questions) {
		this.no_of_questions = no_of_questions;
	}
	public int getQuizDuration() {
		return quizDuration;
	}
	public void setQuizDuration(int quizDuration) {
		this.quizDuration = quizDuration;
	}
	public Long getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}
	public QuizStudentResponse() {
		super();
	}

	
	public QuizStudentResponse(Long id, List<QuestionPaperDTO> questions, String quizTopic, String quizName,
			int no_of_questions, int quizDuration, Long trainerId) {
		super();
		this.id = id;
		this.questions = questions;
		this.quizTopic = quizTopic;
		this.quizName = quizName;
		this.no_of_questions = no_of_questions;
		this.quizDuration = quizDuration;
		this.trainerId = trainerId;
	}
	public List<QuestionPaperDTO> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuestionPaperDTO> questions) {
		this.questions = questions;
	}
	public static QuizStudentResponse toDto(Quiz quiz) {
		List<Question> questions = quiz.getQuestions();
		List<QuestionPaperDTO> questionDTOs = new ArrayList<>();
		for(Question q : questions) {
			questionDTOs.add(QuestionPaperDTO.toQuestionPaperDTO(q));
		}
		return new QuizStudentResponse(quiz.getId(),questionDTOs,
				quiz.getQuizTopic(),quiz.getQuizName(),quiz.getNo_of_questions(),
				quiz.getNo_of_questions()*quiz.getTotal_duration_per_question(),quiz.getTrainerId());
	}
	
	
	
}
