package ru.kornilov.otus.Questuator.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.kornilov.otus.Questuator.config.AppLocale;
import ru.kornilov.otus.Questuator.dao.QuestionDao;
import ru.kornilov.otus.Questuator.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;

@DisplayName("Класс QuestuatorImpl")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class QuestuatorImplTest {

    @Mock
    QuestionDao questionDao;
    @Mock
    AppLocale appLocale;
    @Mock
    private BufferedReader mockBufferedReader;

    private Questuator questuator;

    @BeforeEach
    void init() throws Exception {
        Mockito.when(questionDao.getQuestion())
                .thenReturn(
                        Collections.singletonList(Question.builder().questionText("Question").answer(new String[]{"1", "2", "3"}).tryAnswerIndex("3").build()));
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