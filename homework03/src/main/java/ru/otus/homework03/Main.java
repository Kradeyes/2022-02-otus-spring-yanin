package ru.otus.homework03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework03.service.GetGenreRestService;


@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        ApplicationContext ctx = SpringApplication.run(Main.class, args);
//        GetGenreRestService service = ctx.getBean(GetGenreRestService.class);
//        System.out.println(service.getGenre(1));
    }
}
