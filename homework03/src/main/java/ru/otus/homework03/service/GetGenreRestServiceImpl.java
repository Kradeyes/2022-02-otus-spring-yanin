package ru.otus.homework03.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.otus.homework03.dto.GenreDto;


@Service
public class GetGenreRestServiceImpl implements GetGenreRestService {
    private static final Logger log = LoggerFactory.getLogger(GetGenreRestServiceImpl.class);

    private RestOperations rest = new RestTemplate();

    @Override
    public GenreDto getGenre(long id) {
        log.info("Request");
        return rest.getForObject("http://localhost:8090/api/v1/genre/" + id, GenreDto.class);
    }
}
