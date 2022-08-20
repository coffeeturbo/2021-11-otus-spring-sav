package ru.otus.spring.mvc.http.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.mvc.dto.GenreDto;
import ru.otus.spring.mvc.http.controller.exception.NotFoundException;
import ru.otus.spring.mvc.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping({"/api/v1/genre", "/api/v1/genre/"})
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreDto dto) {
        var newGenre = GenreDto.toDto(genreService.create(dto.toDomainObject()));

        return ResponseEntity.status(HttpStatus.CREATED).body(newGenre);
    }


    @GetMapping({"/api/v1/genre", "/api/v1/genre/"})
    public List<GenreDto> getAll() {

        return genreService.findAllGenres().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping({ "/api/v1/genre/{id}"})
    public GenreDto getById(@PathVariable long id) {
        var genre = genreService.findById(id).orElseThrow(NotFoundException::new);

        return GenreDto.toDto(genre);
    }

    @PutMapping("/api/v1/genre/{id}")
    public GenreDto updateGenre(@PathVariable long id, @RequestBody GenreDto dto) {

        var updatedGenre = genreService.update(dto.toDomainObject());

        return GenreDto.toDto(updatedGenre);
    }

    @DeleteMapping("/api/v1/genre/{id}")
    public void deleteById(@PathVariable long id) {
        genreService.deleteById(id);
    }
}
