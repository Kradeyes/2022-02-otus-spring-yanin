package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework03.dao.BookDao;
import ru.otus.homework03.domain.Book;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    @Override
    public boolean checkTheExistenceOfABookByGenreId(long bookGenreId) {
        List<Book> bookList = bookDao.findBooksByGenreId(bookGenreId);
        return !bookList.isEmpty();
    }

    @Override
    public boolean checkTheExistenceOfABookByAuthorId(long bookAuthorId) {
        List<Book> bookList = bookDao.findBooksByAuthorId(bookAuthorId);
        return !bookList.isEmpty();
    }

    @Override
    public long getIdByBookTitleAndBookAuthorIdAndBookGenreId(String bookTitle, long bookAuthorId, long bookGenreId) {
        long id = 0;
        List<Book> bookList = bookDao.getBookListByBookTitleAndBookAuthorIdAndBookGenreId(bookTitle, bookAuthorId, bookGenreId);
        if (!bookList.isEmpty()) {
            id = bookList.get(0).getId();
        }
        return id;
    }

    @Override
    public void createNewBook(Book book, boolean existingAuthorOrGenre) {
        boolean bookExist = checkTheExistenceOfABook(book);
        if (!bookExist && !existingAuthorOrGenre) {
            bookDao.insert(book);
        }
        if (!bookExist && existingAuthorOrGenre) {
            bookDao.insertWithExistingAuthorOrGenre(book);
        }
    }

    @Override
    public void deleteBook(long bookId) {
        bookDao.deleteBookById(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    private boolean checkTheExistenceOfABook(Book book) {
        List<Book> bookList = bookDao.getAllBooks();
        boolean rsl = false;
        Optional<Book> optionalBook = bookList.stream().filter(x -> x.equals(book)).findFirst();
        if (optionalBook.isPresent()) {
            rsl = true;
        }
        return rsl;
    }
}
