package ru.kornilov.otus.Questuator.service;
/*
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kornilov.otus.Questuator.config.AppLocale;
import ru.kornilov.otus.Questuator.dao.QuestionDao;
import ru.kornilov.otus.Questuator.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;*/

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kornilov.otus.Questuator.config.AppLocale;
import ru.kornilov.otus.Questuator.dao.QuestionDao;
import ru.kornilov.otus.Questuator.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;

@DisplayName("Класс QuestuatorImpl")
@RunWith(MockitoJUnitRunner.class)
class QuestuatorImplTest {

    @Mock
    QuestionDao questionDao;

    @Mock
    AppLocale appLocale;

    private Questuator questuator;
    private BufferedReader mockBufferedReader;

    @BeforeEach
    public void init() throws Exception {
        questionDao = Mockito.mock(QuestionDao.class);
        appLocale = Mockito.mock(AppLocale.class);
        Mockito.when(questionDao.getQuestion())
                .thenReturn(
                        Collections.singletonList(Question.builder().questionText("Question").answer(new String[]{"1", "2", "3"}).tryAnswerIndex("3").build()));
        mockBufferedReader = Mockito.mock(BufferedReader.class);
        questuator = new QuestuatorImpl(mockBufferedReader, questionDao, appLocale);
    }

    @DisplayName("Должен быть правильный ответ")
    @Test
    void shouldHaveTryAnswerTest() throws Exception {
        Mockito.when(mockBufferedReader.readLine())
                .thenReturn("Alex", "Kor", "3");

        questuator.getQuest();

        Assertions.assertEquals(1, questuator.getTryAnswers());
    }

    @DisplayName("Не должно быть правильных ответов")
    @Test
    void shouldHaveFalseAnswerTest() throws IOException {
        Mockito.when(mockBufferedReader.readLine())
                .thenReturn("Alex", "Kor", "1");

        questuator.getQuest();

        Assertions.assertEquals(0, questuator.getTryAnswers());
    }
}