package ru.kornilov.otus.Questuator.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс AppLocale")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class AppLocaleTest {

    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private MessageSource messageSource;

    @DisplayName("Должен выдать сообщение на Английском")
    @Test
    void shouldBeEnglishTests() {
        applicationProperties.setLocale("en");
        AppLocale appLocale = new AppLocale(messageSource, applicationProperties);
        assertEquals(appLocale.getMessage("start.fname"), "To pass the test, enter your Name");
    }

    @DisplayName("Должен выдать сообщение на Русском")
    @Test
    void shouldBeRussianTest() {
        applicationProperties.setLocale("ru");
        AppLocale appLocale = new AppLocale(messageSource, applicationProperties);
        assertEquals(appLocale.getMessage("start.fname"), "Для прохождения теста введите своё Имя");
    }

    @DisplayName("Должен выдать сообщение об ошибке")
    @Test
    void shouldBeExceptionTest() {
        applicationProperties.setLocale("fr");
        AtomicReference<AppLocale> appLocale = null;
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> appLocale.set(new AppLocale(messageSource, applicationProperties)),
                "Locale for language " + "fr" + " is not supported");
    }
}