package ru.kornilov.otus.Questuator.service;

public interface Questuator {
    void getQuest();
    int getTryAnswers();

    void getQuest(String firstName, String lastName);
}