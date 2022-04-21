package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework03.repository.AuthorRepository;
import ru.otus.homework03.domain.Author;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public long getIdByAuthorNameAndSurname(String name, String surname) {
        long id = 0;
        Optional<Author> author = authorRepository.findAuthorByNameAndSurname(name, surname);
        if (author.isPresent()) {
            id = author.get().getId();
        }
        return id;
    }

    @Override
    public void createNewAuthor(Author author) {
        if (!checkTheExistenceOfTheAuthor(author.getName(), author.getSurname())) {
            authorRepository.save(author);
        }
    }

    @Override
    public void deleteAuthor(long authorId) {
        authorRepository.deleteById(authorId);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    private boolean checkTheExistenceOfTheAuthor(String name, String surname) {
        Optional<Author> author = authorRepository.findAuthorByNameAndSurname(name, surname);
        return author.isPresent();
    }
}
