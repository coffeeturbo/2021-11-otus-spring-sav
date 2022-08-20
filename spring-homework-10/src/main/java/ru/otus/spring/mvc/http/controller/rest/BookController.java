package ru.otus.spring.mvc.http.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.mvc.dto.BookDto;
import ru.otus.spring.mvc.http.controller.exception.NotFoundException;
import ru.otus.spring.mvc.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @GetMapping({ "/api/v1/books"})
    public List<BookDto> listBooks() {
        return bookService.getAllBooks().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v1/books/{id}")
    public BookDto getBookById(@PathVariable long id) {
        var book = bookService.getBookById(id).orElseThrow(NotFoundException::new);

        return BookDto.toDto(book);
    }

    @PostMapping("/api/v1/books")
    public ResponseEntity<BookDto> createNewBook(BookDto bookDto) {

        var newBook = bookService.createBook(bookDto.toDomainObject());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BookDto.toDto(newBook));
    }

    @PatchMapping("/api/v1/books/{id}")
    public BookDto editBook(@PathVariable long id, BookDto model) {
        bookService.getBookById(id).orElseThrow(NotFoundException::new);

        return BookDto.toDto(bookService.updateBook(model.toDomainObject()));
    }

    @DeleteMapping("/api/v1/books/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable long id) {
        bookService.deleteBook(id);

        return ResponseEntity.ok().build();
    }
}
