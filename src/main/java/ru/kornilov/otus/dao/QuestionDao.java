package ru.kornilov.otus.dao;

import ru.kornilov.otus.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getQuestion() throws Exception;
}
