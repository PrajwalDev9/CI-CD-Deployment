package com.jwt.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.example.entity.QuizStatus;

public interface QuizStatusRepository extends JpaRepository<QuizStatus, Long> {

}
