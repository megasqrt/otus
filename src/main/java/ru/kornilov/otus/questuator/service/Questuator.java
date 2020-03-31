package ru.kornilov.otus.questuator.service;

public interface Questuator {
    void getQuest();
    int getTryAnswers();

    void getQuest(String firstName, String lastName);
}