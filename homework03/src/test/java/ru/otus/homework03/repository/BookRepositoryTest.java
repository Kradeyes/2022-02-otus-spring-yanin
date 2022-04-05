package ru.otus.homework03.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Genre;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("класс для работы с репозиторием книг должен: ")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("находить книгу в БД по заданным параметрам")
    void findBookByBookTitleAndAuthor_IdAndGenre_Id() {
        Optional<Book> book = bookRepository.findBookByBookTitleAndAuthor_IdAndGenre_Id("someTitle", 1, 1);
        assertEquals(Boolean.TRUE, book.isPresent());
    }

    @Test
    @DisplayName("удалить книгу из БД")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteById() {
        bookRepository.save(new Book(2L, "test", new Author(1L, "test", "test"), new Genre(1L, "test")));
        Optional<Book> savedBook = bookRepository.findById(2L);
        assertEquals(Boolean.TRUE, savedBook.isPresent());
        bookRepository.deleteById(2L);
        Optional<Book> deletedBook = bookRepository.findById(2L);
        assertEquals(Boolean.FALSE, deletedBook.isPresent());
    }

    @Test
    @DisplayName("находить книгу в БД по ID автора")
    void findBookByAuthor_Id() {
        Optional<Book> book = bookRepository.findBookByAuthor_Id(1L);
        assertEquals(Boolean.TRUE, book.isPresent());
    }

    @Test
    @DisplayName("находить книгу в БД по ID жанра")
    void findBookByGenre_Id() {
        Optional<Book> book = bookRepository.findBookByGenre_Id(1L);
        assertEquals(Boolean.TRUE, book.isPresent());
    }
}