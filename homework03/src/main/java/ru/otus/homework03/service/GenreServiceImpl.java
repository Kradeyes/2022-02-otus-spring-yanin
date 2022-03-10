package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework03.dao.GenreDao;
import ru.otus.homework03.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    public long getIdByGenreName(String name) {
        long id = 0;
        List<Genre> genreList = genreDao.getGenreListByGenreName(name);
        if (!genreList.isEmpty()) {
            id = genreList.get(0).getId();
        }
        return id;
    }

    @Override
    public void createNewGenre(String name) {
        if (!checkTheExistenceOfAGenre(name)) {
            genreDao.insert(name);
        }
    }

    @Override
    public void deleteGenre(long genreId) {
        genreDao.deleteGenreById(genreId);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    private boolean checkTheExistenceOfAGenre(String name) {
        List<Genre> genreList = genreDao.getAllGenres();
        boolean rsl = false;
        Optional<Genre> optionalGenre = genreList.stream().filter(x -> x.getGenreName().equals(name)).findFirst();
        if (optionalGenre.isPresent()) {
            rsl = true;
        }
        return rsl;
    }
}
