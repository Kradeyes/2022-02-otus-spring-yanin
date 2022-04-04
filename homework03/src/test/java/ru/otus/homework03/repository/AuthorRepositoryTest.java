package ru.otus.homework03.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework03.domain.Author;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@DisplayName("класс для работы с репозиторием авторов должен: ")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("находить автора в БД")
    void findAuthorByNameAndSurnameTest() {
        Optional<Author> author = authorRepository.findAuthorByNameAndSurname("Ivan", "Ivanov");
        assertEquals(Boolean.TRUE, author.isPresent());
    }

    @Test
    @DisplayName("удалять автора из БД")
    void deleteByIdTest() {
        authorRepository.save(new Author(2, "Ivann", "Ivanov"));
        Optional<Author> savedAuthor = authorRepository.findAuthorByNameAndSurname("Ivann", "Ivanov");
        assertEquals(Boolean.TRUE, savedAuthor.isPresent());
        authorRepository.deleteById(2L);
        Optional<Author> deletedAuthor = authorRepository.findAuthorByNameAndSurname("Ivann", "Ivanov");
        assertEquals(Boolean.FALSE, deletedAuthor.isPresent());
    }
}