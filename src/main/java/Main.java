import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.kornilov.otus.service_busineslevel.Questuator;

public class Main {

    public static void main(String[] args){
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");

        Questuator questuator = context.getBean(Questuator.class);
        questuator.getQuest();
    }
}