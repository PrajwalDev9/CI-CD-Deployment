package com.jwt.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuizStatus {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	private Long quizId;
	public QuizStatus(Long quizId) {
		this.quizId=quizId;
	}

}
