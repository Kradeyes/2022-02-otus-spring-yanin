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
        List<Book> authorList = bookDao.getBookListByBookTitleAndBookAuthorIdAndBookGenreId(bookTitle, bookAuthorId, bookGenreId);
        if (!authorList.isEmpty()) {
            id = authorList.get(0).getId();
        }
        return id;
    }

    @Override
    public void createNewBook(String bookTitle, long bookAuthorId, long bookGenreId) {
        if (!checkTheExistenceOfABook(bookTitle, bookAuthorId, bookGenreId)) {
            bookDao.insert(bookTitle, bookAuthorId, bookGenreId);
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

    private boolean checkTheExistenceOfABook(String bookTitle, long bookAuthorId, long bookGenreId) {
        List<Book> bookList = bookDao.getAllBooks();
        boolean rsl = false;
        Optional<Book> optionalGenre = bookList.stream().filter(x -> x.getBookTitle().equals(bookTitle) &&
                x.getBookAuthorId() == bookAuthorId &&
                x.getBookGenreId() == bookGenreId).findFirst();
        if (optionalGenre.isPresent()) {
            rsl = true;
        }
        return rsl;
    }
}
