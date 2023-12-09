package com.jwt.example.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "VARCHAR(500)")
	private String questionText;
	@Column(columnDefinition = "VARCHAR(255)")
	private String option1;
	@Column(columnDefinition = "VARCHAR(255)")
	private String option2;
	@Column(columnDefinition = "VARCHAR(255)")
	private String option3;
	@Column(columnDefinition = "VARCHAR(255)")
	private String option4;
	private int rightAnswerIndex;
	@ManyToOne
	private Quiz quiz;

	public Question(Long id, String questionText, String option1, String option2, String option3, String option4,
			int rightAnswerIndex, Quiz quiz) {
		super();
		this.id = id;
		this.questionText = questionText;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.rightAnswerIndex = rightAnswerIndex;
		this.quiz = quiz;
	}

	public Question() {
		super();
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

	public int getRightAnswerIndex() {
		return rightAnswerIndex;
	}

	public void setRightAnswerIndex(int rightAnswerIndex) {
		this.rightAnswerIndex = rightAnswerIndex;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
}
