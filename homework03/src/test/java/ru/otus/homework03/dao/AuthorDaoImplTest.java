package ru.otus.homework03.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.generator.AuthorGenerator;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(AuthorDaoImpl.class)
@DisplayName("Dao для работы с авторами должно: ")
class AuthorDaoImplTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("добавлять автора в БД")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void insert() {
        Author expectedAuthor = new Author(2, "Igor", "Ivanov");
        authorDao.insert("Igor", "Ivanov");
        Author actualAuthor = authorDao.getAllAuthors().get(1);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("найти список авторов по имени и фамилии")
    void getAuthorListByAuthorNameAndAuthorSurname() {
        List<Author> expectedAuthorList = AuthorGenerator.generateAuthorsList();
        List<Author> actualAuthorList = authorDao.getAuthorListByAuthorNameAndAuthorSurname("Ivan", "Ivanov");
        assertEquals(expectedAuthorList, actualAuthorList);
    }

    @Test
    @DisplayName("возвращать ожидаемый список авторов")
    void getAllAuthors() {
        List<Author> actualAuthorsList = authorDao.getAllAuthors();
        Author expectedAuthor = new Author(1, "Ivan", "Ivanov");
        assertThat(actualAuthorsList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedAuthor);
    }

    @Test
    @DisplayName("удалять автора")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteAuthorById() {
        authorDao.insert("ForDelete", "ForDelete");
        authorDao.deleteAuthorById(2);
        assertEquals(1, authorDao.getAllAuthors().size());
    }
}