package ru.otus.genre.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.genre.dto.GenreDto;

@RestController
@AllArgsConstructor
public class GenreController {

    @GetMapping("/api/v1/genre/{id}")
    public GenreDto avd(@PathVariable("id") long id) {
        return new GenreDto(1, "message from another service");
    }
}
