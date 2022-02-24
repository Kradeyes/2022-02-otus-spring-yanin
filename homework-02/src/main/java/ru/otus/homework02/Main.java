package ru.otus.homework02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import ru.otus.homework02.service.StudentTestStartService;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class);
        StudentTestStartService studentTestService = context.getBean(StudentTestStartService.class);
        MessageSource messageSource = context.getBean(MessageSource.class);
        studentTestService.start(messageSource);
    }
}
