package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework03.exception.ImpossibilityCreationException;
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
    public Optional<Book> findBookById(long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public boolean checkTheExistenceOfABookByGenreId(long bookGenreId) {
        Optional<Book> book = bookRepository.findBookByGenre_Id(bookGenreId);
        return book.isPresent();
    }

    @Override
    public boolean checkTheExistenceOfABookByAuthorId(long bookAuthorId) {
        Optional<Book> book = bookRepository.findBookByAuthor_Id(bookAuthorId);
        System.out.println(book.isPresent());
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
    public Book createNewBook(Book book, boolean existingAuthorOrGenre) {
        boolean bookExist = checkTheExistenceOfABook(book);
        Book createdBook = new Book();
        if (bookExist) {
            throw new ImpossibilityCreationException();
        }
        if (!existingAuthorOrGenre) {
            createdBook = bookRepository.save(book);
        }
        if (existingAuthorOrGenre) {
            createdBook = em.merge(book);
        }
        return createdBook;
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
        Optional<Book> optionalBook = bookList.stream().filter(x -> x.getBookTitle().equals(book.getBookTitle())
                && x.getAuthor().equals(book.getAuthor()) && x.getGenre().equals(book.getGenre())).findFirst();
        if (optionalBook.isPresent()) {
            rsl = true;
        }
        return rsl;
    }
}
