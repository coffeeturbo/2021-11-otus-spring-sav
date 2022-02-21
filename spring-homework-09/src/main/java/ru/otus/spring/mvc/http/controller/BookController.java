package ru.otus.spring.mvc.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.mvc.dto.AuthorDto;
import ru.otus.spring.mvc.dto.BookDto;
import ru.otus.spring.mvc.dto.GenreDto;
import ru.otus.spring.mvc.http.controller.exception.NotFoundException;
import ru.otus.spring.mvc.mapper.BookMapper;
import ru.otus.spring.mvc.service.AuthorService;
import ru.otus.spring.mvc.service.BookService;
import ru.otus.spring.mvc.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookMapper bookMapper;

    @GetMapping({"/", "/books"})
    public String listBooks(Model model) {
        var books = bookService.getAllBooks().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());

        model.addAttribute("books", books);

        return "book/list";
    }

    @GetMapping("/books/edit/{id}")
    public String editBook(@PathVariable long id, Model model) {
        var book = bookService.getBookById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", BookDto.toDto(book));
        return "book/edit";
    }

    @PostMapping("/books/edit/{id}")
    public String updateBook(@ModelAttribute BookDto bookDto) {
        bookService.updateBook(bookMapper.toDomainObject(bookDto));
        return "redirect:/";
    }

    @GetMapping("/books/{id}")
    public String getBookById(@PathVariable long id, Model model) {
        var book = bookService.getBookById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", BookDto.toDto(book));
        return "book/show";
    }

    @GetMapping("/books/create")
    public String createNewBook() {
        return "book/create";
    }

    @PostMapping("/books/create")
    public String createNewBook(@ModelAttribute BookDto bookDto, Model model) {
        var createdBook = bookService.createBook(bookMapper.toDomainObject(bookDto));
        model.addAttribute("book", BookDto.toDto(createdBook));
        return "redirect:/";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBookById(@PathVariable long id) {
        bookService.deleteBook(id);
        return "redirect:/";
    }

    @ModelAttribute("authors")
    private List<AuthorDto> getAllAuthors() {
        return AuthorDto.toDto(authorService.getAllAuthors());
    }

    @ModelAttribute("genres")
    private List<GenreDto> getAllGenres() {
        return GenreDto.toDto(genreService.findAllGenres());
    }
}
