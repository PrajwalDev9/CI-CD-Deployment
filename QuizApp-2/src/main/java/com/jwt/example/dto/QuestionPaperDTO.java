package com.jwt.example.dto;

import com.jwt.example.entity.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionPaperDTO {
	private Long id;
	private String questionText;
	private String option1;
	private String option2;
	private String option3;
	private String option4;

	public static QuestionPaperDTO toQuestionPaperDTO(Question question) {
		QuestionPaperDTO dto = new QuestionPaperDTO();
		dto.setId(question.getId());
		dto.setQuestionText(question.getQuestionText());
		dto.setOption1(question.getOption1());
		dto.setOption2(question.getOption2());
		dto.setOption3(question.getOption3());
		dto.setOption4(question.getOption4());
		return dto;

	}

}
