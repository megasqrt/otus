package ru.kornilov.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Question {
    private String questionText;
    private String[] answer;
    private String tryAnswerIndex;
}
