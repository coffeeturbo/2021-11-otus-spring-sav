package ru.otus.spring.jdbc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.jdbc.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
