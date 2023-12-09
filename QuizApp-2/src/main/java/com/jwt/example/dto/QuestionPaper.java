package com.jwt.example.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jwt.example.entity.Question;
import com.jwt.example.entity.Quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPaper {
	private Long quizId;
	private List<QuestionPaperDTO> questions;

	public static QuestionPaper toQuestionPaper(Quiz quiz) {
		QuestionPaper questionPaper = new QuestionPaper();
		questionPaper.setQuizId(quiz.getId());
		List<QuestionPaperDTO> dtos = new ArrayList<>();
		for (Question question : quiz.getQuestions()) {
			QuestionPaperDTO dto = QuestionPaperDTO.toQuestionPaperDTO(question);
			dtos.add(dto);
		}
		questionPaper.setQuestions(dtos);
		return questionPaper;

	}

}
