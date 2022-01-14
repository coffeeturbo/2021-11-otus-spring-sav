package ru.otus.spring.jdbc.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class Author {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final List<Book> books;
}
