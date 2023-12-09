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
	public List<Score> getLeaderboardByTopic(String quizTopic) {
	    // Assuming your Score entity has properties like 'studentId', 'quizTopic', 'marks', and 'timeDuration'
	 
	    // Fetch scores for the specified quiz topic
	    List<Score> scores = repository.findByQuizTopic(quizTopic);
	 
	    // Use Java streams to filter out lower scores for each student-quiz combination
	    Map<String, Score> highestScoreByStudent = scores.stream()
	            .collect(Collectors.toMap(
	                    score -> score.getStudentId() + "_" + score.getQuizId(),
	                    score -> score,
	                    (existing, replacement) -> {
	                        if (existing.getMarks() < replacement.getMarks()
	                                || (existing.getMarks() == replacement.getMarks()
	                                && existing.getTotalTimeDuration() > replacement.getTotalTimeDuration())) {
	                            return replacement;
	                        } else {
	                            return existing;
	                        }
	                    }
	            ));
	 
	    // Create a list of Score objects based on the highest scores
	    List<Score> leaderboard = highestScoreByStudent.values().stream()
	            .map(score -> new Score(score.getQuizId(), quizTopic, score.getStudentId(), score.getMarks(), score.getTotalTimeDuration()))
	            .collect(Collectors.toList());
	 
	    // Sort the leaderboard by marks in descending order and time duration in ascending order
	    leaderboard.sort((s1, s2) -> {
	        if (s1.getMarks() != s2.getMarks()) {
	            return Integer.compare(s2.getMarks(), s1.getMarks());
	        } else {
	            return Integer.compare(s1.getTotalTimeDuration(), s2.getTotalTimeDuration());
	        }
	    });
	 
	    return leaderboard;
	}
	public List<Score> getLeaderboardByTopicAndQuizId(String quizTopic, long quizId) {
		// TODO Auto-generated method stub
		
		return repository.findByQuizTopicAndQuizId(quizTopic,quizId);
	}
}
