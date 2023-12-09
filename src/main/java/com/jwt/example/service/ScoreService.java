package com.jwt.example.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.example.entity.Score;
import com.jwt.example.repository.ScoreRepository;

@Service
public class ScoreService {

	@Autowired
	private ScoreRepository repository;

	public Score addScore(Score score) {
		return repository.save(score);
	}
	public List<Score> getLeaderBoardByQuizId(long quizID) {

		return repository.findByQuizId(quizID);

	}

	public List<Score> getLeaderboard() {
     
		return repository.findAll();
	}
      
	public Long canAdd(Score s) {

		Score exitScore = repository.findByQuizIdAndQuizTopicAndStudentIdAndUsernameAndMarksAndTotalTimeDuration(
				s.getQuizId(), s.getQuizTopic(), s.getStudentId(), s.getUsername(), s.getMarks(), s.getTotalTimeDuration());
		
		if(  exitScore == null) {
			return 0l;
		}
		else {
			return  exitScore.getId();
		}
	}
	
	public Score getById(Long id) {
		return repository.findById(id).get();
	}
	


	
}
