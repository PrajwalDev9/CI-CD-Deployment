package com.jwt.example.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Quiz")
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String quizName;
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	private List<Question> questions = new ArrayList<>();
	private String quizTopic;
	private int no_of_questions;
	private int total_duration_per_question;
	private Long trainerId;


	@Override
	public String toString() {
		return "Quiz [id=" + id + ", quizName=" + quizName + ", questions=" + questions + ", quizTopic=" + quizTopic
				+ ", no_of_questions=" + no_of_questions + ", total_duration_per_question="
				+ total_duration_per_question + ", trainerId=" + trainerId ;
	}

	public Long getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}

	public Quiz(Long id, String quizName, List<Question> questions, String quizTopic, int no_of_questions,
			int total_duration_per_question, Long trainerId) {
		super();
		this.id = id;
		this.quizName = quizName;
		this.questions = questions;
		this.quizTopic = quizTopic;
		this.no_of_questions = no_of_questions;
		this.total_duration_per_question = total_duration_per_question;
		this.trainerId = trainerId;
//		this.status = status;
	}

	public Quiz() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String getQuizTopic() {
		return quizTopic;
	}

	public void setQuizTopic(String quizTopic) {
		this.quizTopic = quizTopic;
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

	public void addQuestion(Question question) {
		questions.add(question);

	}

}
