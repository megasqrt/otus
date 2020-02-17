package ru.kornilov.otus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.kornilov.otus.dao.QuestionDao;
import ru.kornilov.otus.dao.QuestionDaoImpl;
import ru.kornilov.otus.service_busineslevel.QuestuatorImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@PropertySource("classpath:application.properties")
@Configuration
public class AppConfig {

    @Bean
    QuestionDao questionDao(@Value("#{'${locale}'=='ru_RU' ? '${file.path}_Ru.csv' : '${file.path}_En.csv'}") String file_path) {
        return new QuestionDaoImpl(file_path);
    }

    @Bean
    BufferedReader reader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public QuestuatorImpl questuator(@Value("#{'${locale}'=='ru_RU' ? 'message_ru' : 'message_en'}") String messageFile, QuestionDao dao, BufferedReader bufferedReader) {
        return new QuestuatorImpl(messageFile, bufferedReader, dao);
    }
}
