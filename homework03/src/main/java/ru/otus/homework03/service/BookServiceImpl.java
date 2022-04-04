package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework03.repository.BookRepository;
import ru.otus.homework03.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean checkTheExistenceOfABookByGenreId(long bookGenreId) {
        Optional<Book> book = bookRepository.findBookByGenre_Id(bookGenreId);
        return book.isPresent();
    }

    @Override
    public boolean checkTheExistenceOfABookByAuthorId(long bookAuthorId) {
        Optional<Book> book = bookRepository.findBookByAuthor_Id(bookAuthorId);
        return book.isPresent();
    }

    @Override
    public long getIdByBookTitleAndBookAuthorIdAndBookGenreId(String bookTitle, long bookAuthorId, long bookGenreId) {
        long id = 0;
        Optional<Book> book = bookRepository.findBookByBookTitleAndAuthor_IdAndGenre_Id(bookTitle, bookAuthorId, bookGenreId);
        if (book.isPresent()) {
            id = book.get().getId();
        }
        return id;
    }

    @Override
    public void createNewBook(Book book, boolean existingAuthorOrGenre) {
        boolean bookExist = checkTheExistenceOfABook(book);
        if (!bookExist && !existingAuthorOrGenre) {
            bookRepository.save(book);
        }
        if (!bookExist && existingAuthorOrGenre) {
            em.merge(book);
        }
    }

    @Override
    public void deleteBook(long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    private boolean checkTheExistenceOfABook(Book book) {
        List<Book> bookList = bookRepository.findAll();
        boolean rsl = false;
        Optional<Book> optionalBook = bookList.stream().filter(x -> x.equals(book)).findFirst();
        if (optionalBook.isPresent()) {
            rsl = true;
        }
        return rsl;
    }
}
