package ru.kornilov.otus.Questuator.dao;

import ru.kornilov.otus.Questuator.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getQuestion() throws Exception;
}
