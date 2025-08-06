package com.aseel.quizservice.model;

import lombok.Data;

@Data
public class QuizDto {
    String  category;
    Integer numQ;
    String title;
}
