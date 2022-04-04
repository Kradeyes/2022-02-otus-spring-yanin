package ru.otus.homework03.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework03.domain.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByBookTitleAndAuthor_IdAndGenre_Id(String bookTitle, long bookAuthorId, long bookGenreId);

    void deleteById(long id);

    Optional<Book> findBookByAuthor_Id(long bookAuthorId);

    Optional<Book> findBookByGenre_Id(long bookGenreId);
}
