package ru.kornilov.otus.questuator.dao;

import ru.kornilov.otus.questuator.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getQuestion() throws Exception;
}
