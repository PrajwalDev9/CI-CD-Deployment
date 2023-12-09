package com.jwt.example.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
	private Long quizId;
	List<Integer> selectedOption=new ArrayList<>();
	private  int totalTimeDuration;
}
