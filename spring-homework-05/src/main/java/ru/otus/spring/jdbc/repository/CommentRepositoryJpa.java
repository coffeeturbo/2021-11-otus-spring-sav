package ru.otus.spring.jdbc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jdbc.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        var query = em.createQuery(
                "select count(c) from Comment c", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Comment save(Comment book) {
        if (book.getId() != 0) {
            return em.merge(book);
        }
        em.persist(book);
        return book;
    }

    @Override
    public void deleteById(long id) {
        var query = em.createQuery("delete from Comment c where c.id = : id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Comment> getById(long id) {
        var query = em.createQuery("select c from Comment c where c.id = :id", Comment.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Comment> getAll() {
        var query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }
}
