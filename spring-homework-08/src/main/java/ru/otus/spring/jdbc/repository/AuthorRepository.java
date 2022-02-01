package ru.otus.spring.jdbc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.jdbc.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findOneByFirstName(String firstName);
    Optional<Author> findOneByFirstNameAndLastName(String firstName, String lastName);
}
