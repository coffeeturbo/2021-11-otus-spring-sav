package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.QuizService;

@RequiredArgsConstructor
@ShellComponent
public class QuizShellCommands {

    private final ApplicationContext context;


    @ShellMethod(key = {"start", "s"}, value = "Starts quiz")
    public void startQuiz() {
        var testingService = context.getBean(QuizService.class);
        testingService.startQuiz();
    }
}
