package ru.kornilov.otus.service_busineslevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kornilov.otus.dao.QuestionDao;
import ru.kornilov.otus.domain.Question;
import ru.kornilov.otus.domain.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QuestuatorImpl implements Questuator {
    private static final Logger sout = LogManager.getLogger(Questuator.class);

    private static String customAnswer = "";
    private static int tryAnswerInt = 0;
    private final QuestionDao questionDao;
    private Student student = Student.builder().build();

    private BufferedReader console;

    QuestuatorImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
        this.console = new BufferedReader(new InputStreamReader(System.in));
    }

    QuestuatorImpl(QuestionDao questionDao, BufferedReader bufferedReader) {
        this.questionDao = questionDao;
        this.console = bufferedReader;
    }

    @Override
    public void getQuest() {
        String tmpFName="";
        String tmpLname="";

        sout.info("Для прохождения теста введите своё Имя");
        tmpFName=readAnswerInConsole();

        sout.info("и фамилию");
        tmpLname=readAnswerInConsole();

        student= Student.builder().firstName(tmpFName).lastName(tmpLname).build();

        sout.info("Для ответа на вопрос, введити цифру правильного варината ответа и нажмите Enter");
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
        sout.info("Правильных ответов " + tryAnswerInt);
    }

    private void showQuestion(Question quest) {
        sout.info("Вопрос #:" + quest.getQuestionText());
        for (int i = 0; i < quest.getAnswer().length; i++) {
            sout.info((i + 1) + ") " + quest.getAnswer()[i]);
        }
        String consoleAnswer = readAnswerInConsole();
        if (!consoleAnswer.isEmpty()) {
            if (consoleAnswer.equals(quest.getTryAnswerIndex())) {
                tryAnswerInt++;
            } else if (quest.getQuestionText().equals("Вам Нравиться данный тест")) {
                if (consoleAnswer.equals("3")) {
                    sout.info("введите свой отзыв о тесте");
                    customAnswer = readAnswerInConsole();
                } else {
                    customAnswer = consoleAnswer;
                }
            }
        }
    }

    private void writeAnswer() {
        sout.debug("Студент " + student.getFullName());
        if (tryAnswerInt == 0)
            sout.debug("набрал " + tryAnswerInt + " правильных ответов");
        else
            sout.debug("набрал " + tryAnswerInt + " правильных ответа");
        switch (customAnswer) {
            case "1":
                sout.debug("и ему нравится этот тест");
                break;
            case "2":
                sout.debug("и ему не нравится этот тетс");
                break;
            default:
                sout.debug("и его мнение о тесте");
                sout.debug(customAnswer);
                break;
        }
    }

    private String readAnswerInConsole() {
        String str = readConsole();
        while (str.isEmpty()) {
            sout.info("пустой ответ не защитывается, повторите ввод");
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
