package com.jwt.example.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.HttpStatusCode;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.jwt.example.dto.QuestionDTO;
import com.jwt.example.dto.QuestionPaper;
import com.jwt.example.dto.QuizDTO;
import com.jwt.example.dto.QuizDTOResponse;
import com.jwt.example.dto.UserLeader;
import com.jwt.example.entity.Question;
import com.jwt.example.entity.Quiz;
import com.jwt.example.entity.QuizStatus;
import com.jwt.example.entity.Score;
import com.jwt.example.entity.UserInfo;
import com.jwt.example.repository.QuizRepository;
import com.jwt.example.repository.QuizStatusRepository;
import com.jwt.example.service.QuestionService;
import com.jwt.example.service.QuizService;
import com.jwt.example.service.QuizStatusService;
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
	private QuizStatusService quizStatusService;

	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private ScoreService scoreService;

	@PostMapping("/trainer/create-quiz")
	@PreAuthorize("hasAuthority('ROLE_TRAINER')")
	public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO, Principal principal) {
		Long trainerId = (long) userService.findIdByUsername(principal.getName());
		Quiz quiz = QuizDTO.fromDTO(quizDTO, trainerId);
		Quiz quiz2 = quizService.addQuiz(quiz);
		System.out.println(quiz2);
		for (QuestionDTO questionDTO : quizDTO.getQuestions()) {
			Question question = QuestionDTO.fromDTO(questionDTO);
			question.setQuiz(quiz);
			questionService.addQuestion(question);
			questionDTO.setId(question.getId());
		}
		quizDTO.setId(quiz.getId());
		quizDTO.setTrainerId(trainerId);
		return new ResponseEntity<>(quizDTO, HttpStatus.CREATED);
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
	public ResponseEntity<List<QuizDTO>> getAllQuiz() {
		List<QuizDTO> quizs = new ArrayList<>();
		for (Quiz quizDTO : quizService.getAllQuiz()) {
			quizs.add(QuizDTO.toDTO(quizDTO));
		}
		System.out.println(quizs);
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
		Quiz quiz = quizService.findQuizById(answerDTO.getQuizId());
		List<Question> questions = quiz.getQuestions();
		List<Integer> answers = new ArrayList<>();
		for (Question question : questions) {
			answers.add(question.getRightAnswerIndex());
		}
		List<Integer> selectedOprion = answerDTO.getSelectedOption();
		int count = 0;
		for (int i = 0; i < answers.size(); i++) {
			if (answers.get(i) == selectedOprion.get(i)) {
				count++;
			}
		}
		Score score = new Score();
		score.setQuizId(quiz.getId());
		score.setTotalTimeDuration(answerDTO.getTotalTimeDuration());
		score.setMarks(count);
		Long sid = (long) userService.findIdByUsername(principal.getName());
		score.setStudentId(sid);
		score.setQuizTopic(quiz.getQuizTopic());

		return new ResponseEntity<>(scoreService.addScore(score), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ROLE_STUDENT')")
	@GetMapping("student/leaderboard/{quizTopic}/{quizId}")
	//java/java-1,java-2,java-3
	public ResponseEntity<List<UserLeader>> getLeaderboardByTopicAndQuizId(@PathVariable String quizTopic,@PathVariable long quizId) {
		List<Score> scores = scoreService.getLeaderboardByTopicAndQuizId(quizTopic,quizId);
		List<UserLeader> userInfos = new ArrayList<>(); 
		for(Score score : scores) {
			UserInfo userinfo = userService.getUserById(score.getStudentId()).get();		
		    UserLeader leader = new  UserLeader( userinfo.getId(),userinfo.getUsername(), userinfo.getEmail(),score.getMarks(),score.getTotalTimeDuration());
			userInfos.add(leader);	
		}
	
		
		Collections.sort(userInfos,Comparator.comparing(UserLeader:: getMarks ).thenComparing(Comparator.comparing(UserLeader::getTotalTimeDuration).reversed()).reversed());
		
		List<UserLeader> topStudents = userInfos.subList(0, Math.min(3, userInfos.size()));	
		return new ResponseEntity<>(topStudents, HttpStatus.OK);
	}

//	@PutMapping("trainer/changeStatus/{quizId}")
//	public ResponseEntity<QuizDTO> changeStatusByTrainer(@PathVariable Long quizId) {
//		Quiz quiz = quizService.findQuizById(quizId);
//		quiz.setStatus(!quiz.getStatus());
//		Quiz updateQuiz = quizService.addQuiz(quiz);
//		return new ResponseEntity<>(QuizDTO.toDTO(updateQuiz), HttpStatus.OK);
//
//	}
//
//	@GetMapping("trainer/quizChangeStatus/{quizId}")
//	public ResponseEntity<QuizStatus> requestQuizChnageStatus(@PathVariable Long quizId) {
//		QuizStatus quizStatus = new QuizStatus(quizId);
//		return new ResponseEntity<>(quizStatusService.addStatus(quizStatus), HttpStatus.OK);
//	}
//
//	@GetMapping("admin/getChangeStatus")
//	public ResponseEntity<List<QuizStatus>> viewAllQuizChangeStatusRequest() {
//		return new ResponseEntity<>(quizStatusService.getAllStatus(), HttpStatus.OK);
//	}
//
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//	@PutMapping("admin/changeStatus/{statusId}")
//	public ResponseEntity<String> changeStatusByAdmin(@PathVariable Long statusId) {
//		String str = quizStatusService.updateQuizStatus(statusId);
//		return new ResponseEntity<>(str, HttpStatus.OK);
//
//	}
}