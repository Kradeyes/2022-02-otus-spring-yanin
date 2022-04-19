package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework03.exception.ImpossibilityCreationException;
import ru.otus.homework03.repository.AuthorRepository;
import ru.otus.homework03.domain.Author;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> findAuthorById(long authorId) {
        return authorRepository.findById(authorId);
    }

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
    public Author createNewAuthor(Author author) {
        Author createdAuthor;
        if (!checkTheExistenceOfTheAuthor(author.getName(), author.getSurname())) {
            createdAuthor = authorRepository.save(author);
        } else {
            throw new ImpossibilityCreationException();
        }
        return createdAuthor;
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
