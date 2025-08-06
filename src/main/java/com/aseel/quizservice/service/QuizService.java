package com.aseel.quizapp.service;

import com.aseel.quizapp.dao.QuestionDAO;
import com.aseel.quizapp.dao.QuizDAO;
import com.aseel.quizapp.model.Question;
import com.aseel.quizapp.model.QuestionWrapper;
import com.aseel.quizapp.model.Quiz;
import com.aseel.quizapp.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizDAO quizDAO;
    private final QuestionDAO questionDAO;

    public QuizService(QuizDAO quizDAO,  QuestionDAO questionDAO) {
        this.quizDAO = quizDAO;
        this.questionDAO = questionDAO;
    }


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDAO.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDAO.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        Optional<Quiz> quiz = quizDAO.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for (Question question : questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
            questionsForUser.add(qw);
        }


        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> gradeQuiz(Integer id, List<Response> responses) {
        Quiz quiz = quizDAO.findById(id).get();
        List<Question> questionsFromDB = quiz.getQuestions();
        int correctAnswers = 0;
        int i = 0;
        for (Response response : responses){
            if(response.getResponse().equals(questionsFromDB.get(i).getRightAnswer())){
                correctAnswers++;
                i++;
            }
        }
        return new ResponseEntity<>(correctAnswers, HttpStatus.OK);
    }
}
