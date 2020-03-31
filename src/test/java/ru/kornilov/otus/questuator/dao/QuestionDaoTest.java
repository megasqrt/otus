package ru.kornilov.otus.questuator.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.kornilov.otus.questuator.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionDao")
class QuestionDaoTest {

    @DisplayName("Должен возвращать вопросы")
    @Test
    void shouldHaveReturnQuestionsTest() throws Exception {
        QuestionDao questionDao = new QuestionDaoImpl("csv/quests_Ru.csv");
        List<Question> questions = questionDao.getQuestion();

        assertThat(questions).hasSize(5);
    }
}