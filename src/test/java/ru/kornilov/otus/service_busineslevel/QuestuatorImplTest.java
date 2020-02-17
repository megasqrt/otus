package ru.kornilov.otus.service_busineslevel;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kornilov.otus.dao.QuestionDao;
import ru.kornilov.otus.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Класс QuestuatorImpl")
@RunWith(MockitoJUnitRunner.class)
public class QuestuatorImplTest {

    @Mock
    QuestionDao questionDao;

    private Questuator questuator;
    private BufferedReader mockBufferedReader;

    @Before
    public void init() throws Exception {
        when(questionDao.getQuestion())
                .thenReturn(
                        Collections.singletonList(Question.builder().questionText("Question").answer(new String[]{"1", "2", "3"}).tryAnswerIndex("3").build()));
        mockBufferedReader = mock(BufferedReader.class);
        questuator = new QuestuatorImpl("message_ru", mockBufferedReader, questionDao);
    }

    @DisplayName("Должен быть правильный ответ")
    @Test
    public void shouldHaveTryAnswerTest() throws IOException {
        Mockito.when(mockBufferedReader.readLine())
                .thenReturn("Alex", "Kor", "3");

        questuator.getQuest();

        assertEquals(1, questuator.getTryAnswers());
    }

    @DisplayName("Не должно быть правильных ответов")
    @Test
    public void shouldHaveFalseAnswerTest() throws IOException {
        Mockito.when(mockBufferedReader.readLine())
                .thenReturn("Alex", "Kor", "1");

        questuator.getQuest();

        assertEquals(0, questuator.getTryAnswers());
    }
}