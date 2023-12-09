package com.jwt.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.example.entity.Question;
import com.jwt.example.repository.QuestionRepository;


@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	public Question addQuestion(Question question) {
		return questionRepository.save(question);
	}

	
	 
}
