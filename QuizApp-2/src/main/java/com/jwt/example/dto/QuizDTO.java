package com.jwt.example.dto;

import java.util.ArrayList;
import java.util.List;

import com.jwt.example.entity.Question;
import com.jwt.example.entity.Quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizDTO {

	private Long id;
	private List<QuestionDTO> questions;
	private String quizTopic;
	private String quizName;
	private int no_of_questions;
	private int total_duration_per_question;
	private Long trainerId;
//	private Boolean status;

	public static QuizDTO toDTO(Quiz quiz) {
		QuizDTO quizDTO = new QuizDTO();
		quizDTO.setId(quiz.getId());
		quizDTO.setQuizName(quiz.getQuizName());
		quizDTO.setQuizTopic(quiz.getQuizTopic());
		quizDTO.setNo_of_questions(quiz.getNo_of_questions());
		quizDTO.setTotal_duration_per_question(quiz.getTotal_duration_per_question());
		quizDTO.setTrainerId(quiz.getTrainerId());
	
//		quizDTO.setStatus(quiz.getStatus());
		List<QuestionDTO> questionDTOs = new ArrayList<>();
		for (Question question : quiz.getQuestions()) {
			QuestionDTO questionDTO = QuestionDTO.toDTO(question);
			questionDTOs.add(questionDTO);
		}
		quizDTO.setQuestions(questionDTOs);
		return quizDTO;
	}

//	public Boolean getStatus() {
//		return status;
//	}
//
//	public void setStatus(Boolean status) {
//		this.status = status;
//	}

	public Long getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
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

	public int getTotal_duration_per_question() {
		return total_duration_per_question;
	}

	public void setTotal_duration_per_question(int total_duration_per_question) {
		this.total_duration_per_question = total_duration_per_question;
	}

	public static Quiz fromDTO(QuizDTO quizDTO, Long trainerId) {
		Quiz quiz = new Quiz();
		quiz.setId(quizDTO.getId());
		quiz.setQuizName(quizDTO.getQuizName());
		quiz.setQuizTopic(quizDTO.getQuizTopic());
		quiz.setNo_of_questions(quizDTO.getNo_of_questions());
		quiz.setTotal_duration_per_question(quizDTO.getTotal_duration_per_question());
		quiz.setTrainerId(trainerId);
//		quiz.setStatus(quizDTO.getStatus());
		return quiz;
	}

}