package ru.kornilov.otus.Questuator.config;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AppLocale {
    private MessageSource messageSource;
    private Locale appLocale;


    public AppLocale(MessageSource messageSource, ApplicationProperties applicationProperties) {
        this.messageSource = messageSource;
        switch (applicationProperties.getLocale()) {
            case ("en"):
                appLocale = Locale.ENGLISH;
                break;
            case ("ru"):
                appLocale = new Locale("ru", "RU");
                break;
            default:
                throw new RuntimeException("Locale for language " + applicationProperties.getLocale() + " is not supported");
        }
    }

    public String getMessage(String message) {
        return messageSource.getMessage(message, null, appLocale);
    }
}
