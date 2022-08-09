package ru.otus.spring.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.mvc.domain.Author;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class AuthorDto {
    private long id;
    private String firstName;
    private String lastName;

    public static AuthorDto toDto(Author author) {

        return new AuthorDto(
                author.getId(),
                author.getFirstName(),
                author.getLastName()
        );
    }

    public static List<AuthorDto> toDto(List<Author> authors) {
        return authors.stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    public Author toDomainObject() {
        return new Author(
                id,
                firstName,
                lastName
        );
    }
}
