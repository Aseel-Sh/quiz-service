package com.aseel.quizservice.service;


import com.aseel.quizservice.dao.QuizDAO;
import com.aseel.quizservice.feign.QuizInterface;
import com.aseel.quizservice.model.QuestionWrapper;
import com.aseel.quizservice.model.Quiz;
import com.aseel.quizservice.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizDAO quizDAO;
    private final QuizInterface quizInterface;

    public QuizService(QuizDAO quizDAO,  QuizInterface quizInterface) {
        this.quizDAO = quizDAO;
        this.quizInterface = quizInterface;
    }


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions =  quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDAO.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        Quiz quiz = quizDAO.findById(id).get();

        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);

        return questions;
    }

    public ResponseEntity<Integer> gradeQuiz(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
