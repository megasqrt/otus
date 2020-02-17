package ru.kornilov.otus.service_busineslevel;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kornilov.otus.dao.QuestionDao;
import ru.kornilov.otus.domain.Question;
import ru.kornilov.otus.domain.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class QuestuatorImpl implements Questuator {

    private static int tryAnswerInt;

    private static final Logger sout = LogManager.getLogger(Questuator.class);
    private final String messageFile;

    private static String customAnswer = "";
    private final BufferedReader console;
    private final QuestionDao questionDao;
    private Student student = Student.builder().build();
    private ResourceBundle mess;

    @Override
    public void getQuest() {
        mess = ResourceBundle.getBundle(messageFile);
        tryAnswerInt = 0;
        String tmpFName;
        String tmpLname;

        sout.info(mess.getString("start.fname"));
        tmpFName=readAnswerInConsole();

        sout.info(mess.getString("start.lname"));
        tmpLname=readAnswerInConsole();

        student= Student.builder().firstName(tmpFName).lastName(tmpLname).build();

        sout.info(mess.getString("quest.instruction"));
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
        sout.info(mess.getString("quest.end") + " " + tryAnswerInt);
    }

    private void showQuestion(Question quest) {
        sout.info(mess.getString("quest.quest") + " #:" + quest.getQuestionText());
        for (int i = 0; i < quest.getAnswer().length; i++) {
            sout.info((i + 1) + ") " + quest.getAnswer()[i]);
        }
        String consoleAnswer = readAnswerInConsole();
        if (!consoleAnswer.isEmpty()) {
            if (consoleAnswer.equals(quest.getTryAnswerIndex())) {
                tryAnswerInt++;
            } else if (quest.getQuestionText().equals(mess.getString("quest.like"))) {
                if (consoleAnswer.equals("3")) {
                    sout.info(mess.getString("quest.write"));
                    customAnswer = readAnswerInConsole();
                } else {
                    customAnswer = consoleAnswer;
                }
            }
        }
    }

    private void writeAnswer() {
        sout.debug(mess.getString("answer.student") + " " + student.getFullName());
        if (tryAnswerInt == 0)
            sout.debug(mess.getString("answer.get") + " " + tryAnswerInt + " " + mess.getString("answer.answer"));
        else
            sout.debug(mess.getString("answer.get") + " " + tryAnswerInt + " " + mess.getString("answer.answer"));
        switch (customAnswer) {
            case "1":
                sout.debug(mess.getString("answer.like"));
                break;
            case "2":
                sout.debug(mess.getString("answer.disLike"));
                break;
            default:
                sout.debug(mess.getString("answer.imho"));
                sout.debug(customAnswer);
                break;
        }
    }

    private String readAnswerInConsole() {
        String str = readConsole();
        while (str.isEmpty()) {
            sout.info(mess.getString("quest.empty"));
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
