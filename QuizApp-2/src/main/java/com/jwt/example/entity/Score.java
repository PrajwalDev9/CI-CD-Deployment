package com.jwt.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Score {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long quizId;
	private String quizTopic;
	private long studentId;
	private int marks;
    private int totalTimeDuration;

//	public Score(long quizId,long studentId, String quizTopic, int marks) {
//		// TODO Auto-generated constructor stub
//		this.quizId=quizId;
//		this.studentId=studentId;
//		this.quizTopic=quizTopic;
//		this.marks=marks;
//	}

	public Score(Long quizId, String quizTopic, long studentId, int marks, int totalTimeDuration) {
		super();
		this.quizId = quizId;
		this.quizTopic = quizTopic;
		this.studentId = studentId;
		this.marks = marks;
		this.totalTimeDuration = totalTimeDuration;
	}


	
}
