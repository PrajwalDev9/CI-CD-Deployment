package com.jwt.example.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.HttpStatusCode;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.core.userdetails.ReactiveUserDetailsServiceResourceFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.jwt.example.dto.AnswerDTO;
import com.jwt.example.dto.LeaderBoard;
import com.jwt.example.dto.QuestionDTO;
import com.jwt.example.dto.QuestionPaper;
import com.jwt.example.dto.QuizDTO;
import com.jwt.example.dto.QuizDTOResponse;
import com.jwt.example.dto.QuizInfo;
import com.jwt.example.dto.QuizStudentResponse;

import com.jwt.example.entity.Question;
import com.jwt.example.entity.Quiz;

import com.jwt.example.entity.Score;
import com.jwt.example.entity.UserInfo;

import com.jwt.example.service.QuestionService;
import com.jwt.example.service.QuizService;

import com.jwt.example.service.ScoreService;
import com.jwt.example.service.UserInfoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/quiz")
@SecurityRequirement(name = "bearerAuth")
public class QuizController {

	@Autowired
	private QuizService quizService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserInfoService userService;

	@Autowired
	private ScoreService scoreService;

	@PostMapping("/trainer/create-quiz")
	@PreAuthorize("hasAuthority('ROLE_TRAINER')")
	public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO, Principal principal) {
		Long trainerId = (long) userService.findIdByUsername(principal.getName());
		HashSet<String> uniqueQuestions = new HashSet<>();

		if (quizDTO.equals(null) || quizDTO.getNo_of_questions() <= 0 || quizDTO.getQuestions().size() == 0
				|| quizDTO.getQuizName().length() == 0 || quizDTO.getQuizName().isBlank()
				|| quizDTO.getQuizTopic().length() == 0 || quizDTO.getQuizTopic().isBlank()
				|| quizDTO.getTotal_duration_per_question() <= 0 
				|| quizService.findByQuizNameAndTrainerId(quizDTO.getQuizName(), trainerId)
				|| quizDTO.getNo_of_questions()!= quizDTO.getQuestions().size()) {
			
			
			return new ResponseEntity<>(quizDTO, HttpStatus.NOT_ACCEPTABLE);

		} else {
			
			Quiz quiz = QuizDTO.fromDTO(quizDTO, trainerId);

			quizService.addQuiz(quiz);
			
			for (QuestionDTO questionDTO : quizDTO.getQuestions()) {

				if (questionDTO.getOption1().length() == 0 || questionDTO.getOption2().length() == 0 
						|| questionDTO.getOption3().length() == 0 || questionDTO.getOption4().length() == 0
						|| questionDTO.getOption1().isBlank() || questionDTO.getOption2().isBlank()
						|| questionDTO.getOption3().isBlank() || questionDTO.getOption4().isBlank()
						|| questionDTO.getQuestionText().isBlank() || questionDTO.getQuestionText().length() == 0
						|| questionDTO.getRightAnswerIndex() <= 0 || questionDTO.getRightAnswerIndex() >= 5
						|| (!uniqueQuestions.add(questionDTO.getQuestionText()))) {
					
                          
					return new ResponseEntity<>(quizDTO, HttpStatus.NOT_ACCEPTABLE);
					
				} else {

					if (questionDTO.getOption1().equalsIgnoreCase(questionDTO.getOption2())
							|| questionDTO.getOption2().equalsIgnoreCase(questionDTO.getOption3())
							|| questionDTO.getOption3().equalsIgnoreCase(questionDTO.getOption4())
							|| questionDTO.getOption4().equalsIgnoreCase(questionDTO.getOption1())) {

						return new ResponseEntity<>(quizDTO, HttpStatus.NOT_ACCEPTABLE);
					}

					else {
						Question question = QuestionDTO.fromDTO(questionDTO);
						question.setQuiz(quiz);
						questionService.addQuestion(question);
						questionDTO.setId(question.getId());
					}

				}

			}

			quizDTO.setId(quiz.getId());
			quizDTO.setTrainerId(trainerId);
			return new ResponseEntity<>(quizDTO, HttpStatus.CREATED);
		}

	}

	@PreAuthorize("hasAuthority('ROLE_TRAINER')")
	@GetMapping("trainer/getAll")
	public ResponseEntity<List<QuizDTOResponse>> getAllQuiz(Principal principal) {
		Long trainerId = (long) userService.findIdByUsername(principal.getName());
		List<Quiz> quizs = quizService.getAllQuizOfATrainer(trainerId);
		List<QuizDTOResponse> quizDTOs = new ArrayList<>();
		for (Quiz quiz : quizs) {
			QuizDTOResponse quizDTO = QuizDTOResponse.toDTO(quiz, principal.getName());
			quizDTOs.add(quizDTO);
		}
		return new ResponseEntity<>(quizDTOs, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ROLE_TRAINER')")
	@GetMapping("trainer/getQuizByTopic/{topic}")
	public ResponseEntity<List<QuizDTOResponse>> findQuizByTopicOfTrainer(@PathVariable String topic,
			Principal principal) {
		Long trainerId = (long) userService.findIdByUsername(principal.getName());
		List<Quiz> quizs = quizService.getAllQuizOfATrainerByTopic(topic, trainerId);
		List<QuizDTOResponse> dtos = new ArrayList<>();
		for (Quiz quiz : quizs) {
			QuizDTOResponse dto = QuizDTOResponse.toDTO(quiz, principal.getName());
			dtos.add(dto);
		}
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ROLE_STUDENT')")
	@GetMapping("student/getAll")
	public ResponseEntity<List<QuizStudentResponse>> getAllQuiz() {

		List<QuizStudentResponse> quizs = new ArrayList<>();
		for (Quiz quizDTO : quizService.getAllQuiz()) {
			quizs.add(QuizStudentResponse.toDto(quizDTO));
		}
		return new ResponseEntity<>(quizs, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ROLE_STUDENT')")
	@GetMapping("student/questionPaper/{id}")
	public ResponseEntity<QuestionPaper> attemptQuiz(@PathVariable Long id) {
		Quiz quiz = quizService.findQuizById(id);
		QuestionPaper paper = QuestionPaper.toQuestionPaper(quiz);
		return new ResponseEntity<>(paper, HttpStatus.OK);

	}

	@PreAuthorize("hasAuthority('ROLE_STUDENT')")
	@PostMapping("student/calculateResult")
	public ResponseEntity<Score> calculateResult(@RequestBody AnswerDTO answerDTO, Principal principal) {

		Quiz quiz = new Quiz();
		if (answerDTO.getQuizId() <= 0) {
			return new ResponseEntity<Score>(new Score(), HttpStatus.NOT_ACCEPTABLE);
		}

		else {
			quiz = quizService.findQuizById(answerDTO.getQuizId());

			List<Question> questions = quiz.getQuestions();
			List<Integer> answers = new ArrayList<>();
			for (Question question : questions) {
				answers.add(question.getRightAnswerIndex());
			}
			List<Integer> selectedOprion = answerDTO.getSelectedOption();
			int count = 0;
			if (selectedOprion.size() != quiz.getNo_of_questions()) {
				
				return new ResponseEntity<>(new Score(), HttpStatus.NOT_ACCEPTABLE);
			} else {
				for (int i = 0; i < answers.size(); i++) {
					if (selectedOprion.get(i) < 0 || selectedOprion.get(i) >= 5) {

						return new ResponseEntity<>(new Score(), HttpStatus.NOT_ACCEPTABLE);
					} else {
						if (answers.get(i) == selectedOprion.get(i)) {
							count++;
						}
					}

				}
			}

			Score score = new Score();
			score.setQuizId(quiz.getId());
			if (answerDTO.getTotalTimeDuration() > 0 && answerDTO.getTotalTimeDuration() <= (quiz.getNo_of_questions()
					* quiz.getTotal_duration_per_question() * 60)) {

				score.setTotalTimeDuration(answerDTO.getTotalTimeDuration());
				score.setMarks(count);
				Long sid = (long) userService.findIdByUsername(principal.getName());
				score.setStudentId(sid);
				score.setQuizTopic(quiz.getQuizTopic());
				UserInfo info = userService.getUserById(score.getStudentId()).get();
				score.setUsername(info.getUsername());
				if (scoreService.canAdd(score) == 0) {

					return new ResponseEntity<>(scoreService.addScore(score), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(scoreService.getById(scoreService.canAdd(score)), HttpStatus.OK);

				}
			} else {
				return new ResponseEntity<>(new Score(), HttpStatus.NOT_ACCEPTABLE);
			}

		}

	}

	@GetMapping("student/leaderboards")
	public List<LeaderBoard> allLeaderBoard() {

		List<LeaderBoard> leaaderBoardByTopics = new ArrayList<>();
		Set<String> topics = quizService.findAllTopics();

		for (String topic : topics) {

			List<Quiz> quizzes = quizService.findQuizByTopic(topic);

			for (Quiz quiz : quizzes) {

				List<Score> scores = scoreService.getLeaderBoardByQuizId(quiz.getId());

				if (scores.size() == 0) {
					continue;
				} else {

					List<QuizInfo> quizInfos = new ArrayList<>();

					scores.sort(Comparator.comparing(Score::getMarks).reversed()
							.thenComparing(Score::getTotalTimeDuration));
					
					List<Score> top3Scores = scores.stream().limit(3).collect(Collectors.toList());

					QuizInfo info = new QuizInfo();
					info.setQuizName(quiz.getQuizName());
					info.setToppers(top3Scores);
					quizInfos.add(info);
					LeaderBoard leaaderBoardByTopic = new LeaderBoard();
					leaaderBoardByTopic.setQuizTopic(topic);
					leaaderBoardByTopic.setQuizInfo(quizInfos);
					leaaderBoardByTopics.add(leaaderBoardByTopic);
				}

			}

		}

		return leaaderBoardByTopics;
	}

}
