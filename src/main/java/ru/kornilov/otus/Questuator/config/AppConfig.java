package ru.kornilov.otus.Questuator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.kornilov.otus.Questuator.dao.QuestionDao;
import ru.kornilov.otus.Questuator.dao.QuestionDaoImpl;
import ru.kornilov.otus.Questuator.service.QuestuatorImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class AppConfig {
    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    QuestionDao questionDao() {
        String file_path = applicationProperties.getLocale().equals("ru") ? applicationProperties.getQpath() + "_Ru.csv" : applicationProperties.getQpath() + "_En.csv";
        return new QuestionDaoImpl(file_path);
    }

    @Bean
    BufferedReader reader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public QuestuatorImpl questuator(AppLocale appLocale, QuestionDao dao, BufferedReader bufferedReader) {
        return new QuestuatorImpl(bufferedReader, dao, appLocale);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(applicationProperties.getMessageBaseName());
        messageSource.setDefaultEncoding(applicationProperties.getEncoding());
        return messageSource;
    }
}
