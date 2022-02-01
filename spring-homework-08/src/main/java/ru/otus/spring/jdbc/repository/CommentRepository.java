package ru.otus.spring.jdbc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.jdbc.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
