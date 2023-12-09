package com.jwt.example.dto;

import java.util.ArrayList;

import java.util.List;

import com.jwt.example.entity.Question;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

	private Long id;
	private String questionText;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private int rightAnswerIndex;

	public static QuestionDTO toDTO(Question question) {
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setId(question.getId());
		questionDTO.setQuestionText(question.getQuestionText());
		questionDTO.setRightAnswerIndex(question.getRightAnswerIndex());
		questionDTO.setOption1(question.getOption1());
		questionDTO.setOption2(question.getOption2());
		questionDTO.setOption3(question.getOption3());
		questionDTO.setOption4(question.getOption4());
		return questionDTO;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public static Question fromDTO(QuestionDTO questionDTO) {
		Question question = new Question();
		question.setId(questionDTO.getId());
		question.setQuestionText(questionDTO.getQuestionText());
		question.setRightAnswerIndex(questionDTO.getRightAnswerIndex());
		question.setOption1(questionDTO.getOption1());
		question.setOption2(questionDTO.getOption2());
		question.setOption3(questionDTO.getOption3());
		question.setOption4(questionDTO.getOption4());
		return question;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public int getRightAnswerIndex() {
		return rightAnswerIndex;
	}

	public void setRightAnswerIndex(int rightAnswerIndex) {
		this.rightAnswerIndex = rightAnswerIndex;
	}

}