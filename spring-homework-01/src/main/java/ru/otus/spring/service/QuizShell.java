package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class QuizShell {

    private final ApplicationContext context;


    @ShellMethod(key = {"start", "s"}, value = "Starts quiz")
    public void startQuiz() {
        var testingService = context.getBean(QuizService.class);
        testingService.startQuiz();
    }
}
