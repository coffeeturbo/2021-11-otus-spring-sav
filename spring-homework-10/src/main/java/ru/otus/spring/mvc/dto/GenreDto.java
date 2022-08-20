package ru.otus.spring.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.mvc.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class GenreDto {

    private long id;
    private String name;

    public static GenreDto toDto(Genre genre) {

        return new GenreDto(
                genre.getId(),
                genre.getName()
        );
    }

    public static List<GenreDto> toDto(List<Genre> genres) {
        return genres.stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    public Genre toDomainObject() {
        return new Genre(
                id,
                name
        );
    }
}
