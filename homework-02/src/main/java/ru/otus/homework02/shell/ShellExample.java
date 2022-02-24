package ru.otus.homework02.shell;

import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework02.service.StudentTestStartService;

@ShellComponent
public class ShellExample {
    private final StudentTestStartService service;
    private final MessageSource messageSource;

    public ShellExample(StudentTestStartService service, MessageSource messageSource) {
        this.service = service;
        this.messageSource = messageSource;
    }

    @ShellMethod(key = {"start", "s"}, value = "Сommand to start testing")
    public void runTesting() {
        service.start(messageSource);
    }
}
