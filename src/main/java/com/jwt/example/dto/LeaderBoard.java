package com.jwt.example.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jwt.example.entity.Quiz;
import com.jwt.example.entity.Score;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeaderBoard {
	private String quizTopic;
	private List<QuizInfo> quizInfo;

}
