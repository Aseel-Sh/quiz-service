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

//        Optional<Quiz> quiz = quizDAO.findById(id);
//        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
//        for (Question question : questionsFromDB) {
//            QuestionWrapper qw = new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
//            questionsForUser.add(qw);
//        }


        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> gradeQuiz(Integer id, List<Response> responses) {
//        Quiz quiz = quizDAO.findById(id).get();
//        List<Question> questionsFromDB = quiz.getQuestions();
        int correctAnswers = 0;
//        int i = 0;
//        for (Response response : responses){
//            if(response.getResponse().equals(questionsFromDB.get(i).getRightAnswer())){
//                correctAnswers++;
//                i++;
//            }
//        }
        return new ResponseEntity<>(correctAnswers, HttpStatus.OK);
    }
}
