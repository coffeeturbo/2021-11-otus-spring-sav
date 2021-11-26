import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.QuizServiceImpl;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var testingService = context.getBean(QuizServiceImpl.class);
        testingService.startQuiz();
    }
}
