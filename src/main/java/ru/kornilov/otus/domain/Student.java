package ru.kornilov.otus.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
    private String lastName;
    private String firstName;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
