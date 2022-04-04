package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework03.dao.AuthorDao;
import ru.otus.homework03.domain.Author;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public long getIdByAuthorNameAndSurname(String name, String surname) {
        long id = 0;
        List<Author> authorList = authorDao.getAuthorListByAuthorNameAndAuthorSurname(name, surname);
        if (!authorList.isEmpty()) {
            id = authorList.get(0).getId();
        }
        return id;
    }

    @Override
    public void createNewAuthor(Author author) {
        if (!checkTheExistenceOfTheAuthor(author.getName(), author.getSurname())) {
            authorDao.insert(author);
        }
    }

    @Override
    public void deleteAuthor(long authorId) {
        authorDao.deleteAuthorById(authorId);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }

    private boolean checkTheExistenceOfTheAuthor(String name, String surname) {
        List<Author> authorList = authorDao.getAllAuthors();
        boolean rsl = false;
        Optional<Author> optionalAuthor = authorList.stream().filter(x -> x.getName().equals(name) &&
                x.getSurname().equals(surname)).findFirst();
        if (optionalAuthor.isPresent()) {
            rsl = true;
        }
        return rsl;
    }
}
