package ru.otus.spring.mvc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.mvc.domain.Author;
import ru.otus.spring.mvc.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Override
    public Optional<Author> findAuthorById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return repository.findAll();
    }
}
