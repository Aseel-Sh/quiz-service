package com.aseel.quizservice.controller;

import lombok.Data;

@Data
public class QuizDto {
    String  category;
    Integer numQ;
    String title;
}
