package ru.kornilov.otus.Questuator.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.kornilov.otus.Questuator.service.Questuator;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {

    private final Questuator questuator;

    private String firstName;
    private String lastName;

    @ShellMethod(value = "Login command and parametr --first-name --last-name", key = {"l", "login"})
    public String login(@ShellOption String firstName, @ShellOption String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        return String.format("Добро пожаловать: %s %s", firstName, lastName);
    }

    @ShellMethod(value = "Start questuator", key = {"s", "start", "startQuest"})
    @ShellMethodAvailability(value = "isAuthorizedUser")
    public String publishEvent() {
        questuator.getQuest(this.firstName, this.lastName);
        return "Событие опубликовано";
    }

    private Availability isAuthorizedUser() {
        return firstName == null&&lastName==null ? Availability.unavailable("Сначала залогиньтесь (коммандой login с параметром --first-name --last-name)") : Availability.available();
    }

}
