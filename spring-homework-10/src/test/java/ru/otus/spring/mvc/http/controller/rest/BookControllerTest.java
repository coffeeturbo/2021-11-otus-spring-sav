package ru.otus.spring.mvc.http.controller.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.mvc.domain.Author;
import ru.otus.spring.mvc.domain.Book;
import ru.otus.spring.mvc.domain.Genre;
import ru.otus.spring.mvc.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final String API_BOOKS_URL = "/api/v1/books";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    private Author authorFirst;
    private Author authorSecond;
    private Genre genreDrama;
    private Genre genreComedy;


    @BeforeEach
    void setUp() {
        authorFirst = new Author(1L, "FristName1", "LastName1");
        authorSecond = new Author(2L, "secondName2", "LastName2");
        genreDrama = new Genre(1L, "drama");
        genreComedy = new Genre(2L, "comedy");
    }


    @SneakyThrows
    @Test
    void shouldReturnListOfBooks() {

        var books = List.of(
                new Book(1L, authorFirst, "Book name first", List.of(genreDrama)),
                new Book(2L, authorSecond, "Book name Second", List.of(genreComedy))
        );

        given(bookService.getAllBooks())
                .willReturn(books);

        mvc.perform(get(API_BOOKS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Book name first"))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].name").value("Book name Second"))
                .andExpect(jsonPath("$[1].id").value("2"));
    }

    @SneakyThrows
    @Test
    void shouldReturnBookById() {

        var book = new Book(1L, authorFirst, "Book name first", List.of(genreDrama));

        given(bookService.getBookById(1))
                .willReturn(Optional.of(book));

        mvc.perform(get(API_BOOKS_URL + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book name first"))
                .andExpect(jsonPath("$.id").value("1"));
    }


    @SneakyThrows
    @Test
    void shouldCreateBook() {
        var newBook = new Book(0, authorFirst, "Book name first", List.of(genreDrama));
        var expected = new Book(1L, authorFirst, "Book name first", List.of(genreDrama));

        given(bookService.createBook(ArgumentMatchers.any(Book.class)))
                .willReturn(expected);


        mvc.perform(post(API_BOOKS_URL)
                        .param("name", "Book name first")
                        .param("id", "0")
                        .param("authorId", "1")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Book name first"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @SneakyThrows
    @Test
    void shouldEditBook() {
        var book = new Book(1L, authorFirst, "Book name first", List.of(genreDrama));

        given(bookService.getBookById(1))
                .willReturn(Optional.of(book));

        given(bookService.updateBook(ArgumentMatchers.any(Book.class)))
                .willReturn(book);


        mvc.perform(patch(API_BOOKS_URL + "/" + 1)
                        .param("name", "Book name first")
                        .param("id", "0")
                        .param("authorId", "1")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("Book name first"))
                .andExpect(jsonPath("$.id").value("1"));
    }


    @SneakyThrows
    @Test
    void shouldDeleteBookById() {

        willDoNothing()
                .given(bookService)
                .deleteBook(1);


        mvc.perform(delete(API_BOOKS_URL + "/" + 1))
                .andExpect(status().isOk());

        verify(bookService)
                .deleteBook(anyLong());
    }
}