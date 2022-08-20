package ru.otus.spring.mvc.http.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.mvc.dto.AuthorDto;
import ru.otus.spring.mvc.http.controller.exception.NotFoundException;
import ru.otus.spring.mvc.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;


//@CrossOrigin
@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    @GetMapping("/api/v1/authors")
    public List<AuthorDto> getAuthors() {
        return authorService.getAllAuthors().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/v1/authors")
    public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto author) {
        authorService.save(author.toDomainObject());
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @PatchMapping("/api/v1/authors/{id}")
    public ResponseEntity<AuthorDto> update(@RequestBody AuthorDto author, @PathVariable int id) {
        authorService.save(author.toDomainObject());
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping("/api/v1/authors/{id}")
    public ResponseEntity<AuthorDto> getById(@PathVariable int id) {
        var author = authorService.findAuthorById(id).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(AuthorDto.toDto(author), HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/authors/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        authorService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
