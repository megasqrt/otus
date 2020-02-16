package ru.kornilov.otus.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import ru.kornilov.otus.domain.Question;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final String file_path;

    private static List<Question> readByRow(Reader reader) throws Exception {
        List<Question> questionList = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(parser)
                .build();

        String[] line;
        while ((line = csvReader.readNext()) != null) {
            questionList.add(Question.builder()
                    .questionText(line[0])
                    .answer(new String[]{line[1], line[2], line[3]})
                    .tryAnswerIndex(line[4]).build());
        }
        reader.close();
        csvReader.close();
        return questionList;
    }

    @Override
    public List<Question> getQuestion() throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(file_path).toURI()));
        return readByRow(reader);
    }
}
