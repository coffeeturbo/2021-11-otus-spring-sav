package ru.otus.spring.mvc.http.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.mvc.domain.Author;
import ru.otus.spring.mvc.domain.Book;
import ru.otus.spring.mvc.dto.BookDto;
import ru.otus.spring.mvc.mapper.BookMapper;
import ru.otus.spring.mvc.service.AuthorService;
import ru.otus.spring.mvc.service.BookService;
import ru.otus.spring.mvc.service.GenreService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private BookMapper bookMapper;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListBooks() throws Exception {
        List<Book> books = List.of(
                new Book(1, new Author(), "testBookname", Collections.emptyList()),
                new Book(2, new Author(), "testBookname2", Collections.emptyList()),
                new Book(3, new Author(), "testBookname3", Collections.emptyList())
        );

        given(bookService.getAllBooks()).willReturn(books);
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/list"))
                .andExpect(model().attribute("books", hasSize(3)))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("name", is("testBookname"))
                        )
                )));

        verify(bookService, times(1)).getAllBooks();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void testEditBook() throws Exception {
        var book = new Book(1, new Author(), "testBookname2", Collections.emptyList());

        given(bookService.getBookById(anyLong())).willReturn(Optional.of(book));
        mvc.perform(get("/books/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attribute("book", hasProperty("id", is(1L))))
                .andExpect(model().attribute("book", hasProperty("name", is("testBookname2"))));

        verify(bookService, times(1)).getBookById(anyLong());
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void testUpdateBook() throws Exception {
        var book = new Book(1, new Author(1, "Test", "test2"), "testBookname2", Collections.emptyList());
        given(bookMapper.toDomainObject(ArgumentMatchers.any(BookDto.class)))
                .willReturn(book);

        given(bookService.updateBook(ArgumentMatchers.any(Book.class)))
                .willReturn(book);

        mvc.perform(post("/books/edit/1")
                        .param("authorId", "1")
                        .param("id", "1")
                        .param("name", "name")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(bookService, times(1)).updateBook(ArgumentMatchers.any(Book.class));
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void testGetBookById() throws Exception {
        var book = new Book(1, new Author(), "testBookname", Collections.emptyList());

        given(bookService.getBookById(anyLong())).willReturn(Optional.of(book));
        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/show"))
                .andExpect(model().attribute("book", hasProperty("id", is(1L))))
                .andExpect(model().attribute("book", hasProperty("name", is("testBookname"))))
                .andExpect(content().string(containsString("Book Info Show")));

        verify(bookService, times(1)).getBookById(anyLong());
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void testCreateNewBook() throws Exception {
        var book = new Book(12, new Author(1, "Test", "test2"), "testBookname2", Collections.emptyList());

        given(bookMapper.toDomainObject(ArgumentMatchers.any(BookDto.class)))
                .willReturn(new Book());

        given(bookService.createBook(ArgumentMatchers.any(Book.class)))
                .willReturn(book);

        mvc.perform(post("/books/create")
                                .param("authorId", "1")
                                .param("id", "0")
                                .param("name", "name")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(bookService, times(1)).createBook(ArgumentMatchers.any(Book.class));
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void testDeleteBookById() throws Exception {
        mvc.perform(post("/books/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(bookService, times(1)).deleteBook(ArgumentMatchers.anyLong());
        verifyNoMoreInteractions(bookService);
    }
}
