package com.jwt.example.dto;

import java.util.List;

import com.jwt.example.entity.Score;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizInfo {
	private String quizName;
	private List<Score> toppers;
}
