package ru.otus.homework03.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework03.domain.Author;

import javax.persistence.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    public List<Author> getAuthorListByAuthorNameAndAuthorSurname(String name, String surname) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name and a.surname = :surname",
                Author.class);
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        return query.getResultList();
    }

    @Override
    public List<Author> getAllAuthors() {
        TypedQuery<Author> query = em.createQuery("select a from Author a",
                Author.class);
        return query.getResultList();
    }

    @Override
    public void deleteAuthorById(long id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
