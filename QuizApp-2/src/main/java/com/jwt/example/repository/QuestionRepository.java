package com.jwt.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.example.entity.Question;


@Repository
public interface QuestionRepository extends JpaRepository<Question,Long>{

}
