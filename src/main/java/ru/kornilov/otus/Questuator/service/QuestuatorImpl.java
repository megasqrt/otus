package ru.kornilov.otus.Questuator.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kornilov.otus.Questuator.config.AppLocale;
import ru.kornilov.otus.Questuator.dao.QuestionDao;
import ru.kornilov.otus.Questuator.domain.Question;
import ru.kornilov.otus.Questuator.domain.Student;

import java.io.BufferedReader;
import java.io.IOException;

@RequiredArgsConstructor
public class QuestuatorImpl implements Questuator {

    private static int tryAnswerInt;

    private static final Logger sout = LogManager.getLogger(Questuator.class);

    private static String customAnswer;
    private final BufferedReader console;
    private final QuestionDao questionDao;
    private Student student;

    private final AppLocale appLocale;


    @Override
    public void getQuest() {
        customAnswer = "";
        student = Student.builder().build();
        tryAnswerInt = 0;
        String tmpFName;
        String tmpLname;

        sout.info(appLocale.getMessage("start.fname"));
        tmpFName = readAnswerInConsole();

        sout.info(appLocale.getMessage("start.lname"));
        tmpLname = readAnswerInConsole();

        student = Student.builder().firstName(tmpFName).lastName(tmpLname).build();

        sout.info(appLocale.getMessage("quest.instruction"));
        startQuest();
        writeAnswer();
    }

    @Override
    public int getTryAnswers() {
        return tryAnswerInt;
    }

    private void startQuest() {
        try {
            questionDao.getQuestion().forEach(this::showQuestion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sout.info(appLocale.getMessage("quest.end") + " " + tryAnswerInt);
    }

    private void showQuestion(Question quest) {
        sout.info(appLocale.getMessage("quest.quest") + " #:" + quest.getQuestionText());
        for (int i = 0; i < quest.getAnswer().length; i++) {
            sout.info((i + 1) + ") " + quest.getAnswer()[i]);
        }
        String consoleAnswer = readAnswerInConsole();
        if (!consoleAnswer.isEmpty()) {
            if (consoleAnswer.equals(quest.getTryAnswerIndex())) {
                tryAnswerInt++;
            } else if (quest.getQuestionText().equals(appLocale.getMessage("quest.like"))) {
                if (consoleAnswer.equals("3")) {
                    sout.info(appLocale.getMessage("quest.write"));
                    customAnswer = readAnswerInConsole();
                } else {
                    customAnswer = consoleAnswer;
                }
            }
        }
    }

    private void writeAnswer() {
        sout.info(appLocale.getMessage("answer.student") + " " + student.getFullName());
        sout.info(appLocale.getMessage("answer.get") + " " + tryAnswerInt + " " + appLocale.getMessage("answer.answer"));
        switch (customAnswer) {
            case "1":
                sout.info(appLocale.getMessage("answer.like"));
                break;
            case "2":
                sout.info(appLocale.getMessage("answer.disLike"));
                break;
            default:
                sout.info(appLocale.getMessage("answer.imho"));
                sout.info(customAnswer);
                break;
        }
    }

    private String readAnswerInConsole() {
        String str = readConsole();
        while (str.isEmpty()) {
            sout.info(appLocale.getMessage("quest.empty"));
            str = readConsole();
        }
        return str;
    }

    private String readConsole() {
        String readLine = "";
        try {
            readLine = console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readLine;
    }
}
