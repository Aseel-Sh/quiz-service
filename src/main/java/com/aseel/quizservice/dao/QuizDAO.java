package com.aseel.quizservice.dao;

import com.aseel.quizservice.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuizDAO extends JpaRepository<Quiz, Integer> {
}
