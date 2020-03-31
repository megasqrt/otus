package ru.kornilov.otus.questuator.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
    private final String lastName;
    private final String firstName;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
