package ru.otus.spring.jdbc.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
public class Author {
    private final long id;
    private final String firstName;
    private final String lastName;
}
