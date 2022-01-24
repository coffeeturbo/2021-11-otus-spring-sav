package ru.otus.spring.jdbc.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.jdbc.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    void deleteById(long id);

    Optional<Comment> getById(long id);

    @Override
    List<Comment> findAll();
}
