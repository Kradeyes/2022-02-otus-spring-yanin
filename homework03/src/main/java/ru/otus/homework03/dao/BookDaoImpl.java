package ru.otus.homework03.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework03.domain.Book;

import javax.persistence.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Book book) {
        em.persist(book);
    }

    @Override
    public void insertWithExistingAuthorOrGenre(Book book) {
        em.merge(book);
    }

    @Override
    public List<Book> getBookListByBookTitleAndBookAuthorIdAndBookGenreId(String bookTitle, long bookAuthorId, long bookGenreId) {
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                        "where b.bookTitle = :booktitle and b.author.id = :bookauthorid and b.genre.id = :bookgenreid",
                Book.class);
        query.setParameter("booktitle", bookTitle);
        query.setParameter("bookauthorid", bookAuthorId);
        query.setParameter("bookgenreid", bookGenreId);
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBooks() {
        EntityGraph<?> entityGraph = em.getEntityGraph("otus-book-authors-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteBookById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Book> findBooksByGenreId(long bookGenreId) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.genre.id = :bookgenreid",
                Book.class);
        query.setParameter("bookgenreid", bookGenreId);
        return query.getResultList();
    }

    @Override
    public List<Book> findBooksByAuthorId(long bookAuthorId) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.author.id = :bookauthorid",
                Book.class);
        query.setParameter("bookauthorid", bookAuthorId);
        return query.getResultList();
    }
}
