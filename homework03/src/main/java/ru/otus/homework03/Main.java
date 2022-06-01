package ru.otus.homework03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.ApplicationContext;
import ru.otus.homework03.service.GetGenreRestService;


@SpringBootApplication
@EnableCircuitBreaker
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        GetGenreRestService service = ctx.getBean(GetGenreRestService.class);
        System.out.println(service.getGenre(1));
    }
}
