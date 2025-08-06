package com.aseel.quizapp.dao;

import com.aseel.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuizDAO extends JpaRepository<Quiz, Integer> {
}
