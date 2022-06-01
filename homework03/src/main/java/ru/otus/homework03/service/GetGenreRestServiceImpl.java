package ru.otus.homework03.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.otus.homework03.dto.GenreDto;

import java.util.Arrays;

@Service
public class GetGenreRestServiceImpl implements GetGenreRestService {
    private static final Logger log = LoggerFactory.getLogger(GetGenreRestServiceImpl.class);

    private RestOperations rest = new RestTemplate();

    @HystrixCommand(commandProperties ={
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds" , value = "3000")} ,
            fallbackMethod = "defaultGenreFromGenreService")
    @Override
    public GenreDto getGenre(long id) {
        log.info("Request");
        try {
            System.out.println("Сплю 5 секунд");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return rest.getForObject("http://localhost:8090/api/v1/genre/" + id, GenreDto.class);
    }

    private GenreDto defaultGenreFromGenreService(long id) {
        log.info("The service is currently unavailable, the default genre is set. Please try to get the current genre later");
        return new GenreDto(id, "SomeDefaultGenre");
    }
}
