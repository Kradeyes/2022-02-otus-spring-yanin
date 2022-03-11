package ru.otus.homework03.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework03.domain.Commentary;

import javax.persistence.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentaryDaoImpl implements CommentaryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Commentary commentary) {
        em.persist(commentary);
    }

    @Override
    public List<Commentary> getAllCommentariesByBookId(long bookId) {
        EntityGraph<?> entityGraph = em.getEntityGraph("otus-commentary-books-entity-graph");
        TypedQuery<Commentary> query = em.createQuery("select c from Commentary c where c.book.id = :bookid", Commentary.class);
        query.setParameter("bookid", bookId);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Commentary> getCommentaryListByName(String name) {
        TypedQuery<Commentary> query = em.createQuery("select c from Commentary c where c.name= :name", Commentary.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void deleteCommentaryById(long id) {
        Query query = em.createQuery("delete from Commentary c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
