package ru.kornilov.otus.questuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.kornilov.otus.questuator.service.Questuator;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        Questuator questuator = ctx.getBean(Questuator.class);
        questuator.getQuest();
    }
}