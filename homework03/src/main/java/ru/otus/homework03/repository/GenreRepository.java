package ru.otus.homework03.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework03.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findGenreByGenreName(String name);

    void deleteGenreById(long id);
}
