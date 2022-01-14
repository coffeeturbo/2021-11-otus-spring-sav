package ru.otus.spring.jdbc.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Book {
    private final long id;
    private final Author author;
    private final String name;
    private final List<Genre> genres;
}
