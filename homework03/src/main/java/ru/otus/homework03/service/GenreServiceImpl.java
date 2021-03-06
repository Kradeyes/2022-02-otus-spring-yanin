package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework03.repository.GenreRepository;
import ru.otus.homework03.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public long getIdByGenreName(String name) {
        long id = 0;
        Optional<Genre> genre = genreRepository.findGenreByGenreName(name);
        if (genre.isPresent()) {
            id = genre.get().getId();
        }
        return id;
    }

    @Override
    public void createNewGenre(Genre genre) {
        if (!checkTheExistenceOfAGenre(genre.getGenreName())) {
            genreRepository.save(genre);
        }
    }

    @Override
    public void deleteGenre(long genreId) {
        genreRepository.deleteGenreById(genreId);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    private boolean checkTheExistenceOfAGenre(String name) {
        Optional<Genre> genre = genreRepository.findGenreByGenreName(name);
        return genre.isPresent();
    }
}
