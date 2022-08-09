package ru.otus.spring.mvc.http.controller.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.mvc.domain.Author;
import ru.otus.spring.mvc.service.AuthorService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest extends BaseApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void shouldReturnAuthors() throws Exception {

        var authors = List.of(
                new Author(1L, "FristName1", "LastName1"),
                new Author(2L, "FristName2", "LastName2")
        );

        given(authorService.getAllAuthors()).willReturn(authors);

        mvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].length()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("FristName1")))
                .andExpect(jsonPath("$[0].lastName", is("LastName1")))
                .andExpect(jsonPath("$[1].length()", is(3)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("FristName2")))
                .andExpect(jsonPath("$[1].lastName", is("LastName2")));

        verify(authorService, times(1)).getAllAuthors();
        verifyNoMoreInteractions(authorService);
    }

    @Test
    void shouldCreateAuthor() throws Exception {

        var author = new Author();
        author.setFirstName("Alex");
        author.setLastName("Pushkin");

        given(authorService.save(ArgumentMatchers.any(Author.class))).willReturn(author);
        mvc.perform(
                        post("/api/v1/authors").content(asJsonString(author)).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
        verify(authorService, times(1)).save(ArgumentMatchers.any(Author.class));
        verifyNoMoreInteractions(authorService);
    }

    @ParameterizedTest(name = "{index} {0} {1} {2}")
    @CsvSource({"1, Alex, Pushkin", "2, Ivan, Bunin", "3, Peter, Kozlov"})
    void shouldUpdateAuthor(int id, String firstName, String lastName) throws Exception {

        var author = new Author(id, firstName, lastName);

        given(authorService.save(ArgumentMatchers.any(Author.class))).willReturn(author);
        mvc.perform(
                        patch("/api/v1/authors/" + id).content(asJsonString(author)).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.firstName", is(firstName)))
                .andExpect(jsonPath("$.lastName", is(lastName)));

        verify(authorService, times(1)).save(ArgumentMatchers.any(Author.class));
        verifyNoMoreInteractions(authorService);
    }

    @Test
    void shouldReturnAuthorById() throws Exception {
        var author = new Author();
        author.setFirstName("Alex");
        author.setLastName("Lermontov");

        given(authorService.findAuthorById(ArgumentMatchers.anyLong())).willReturn(Optional.of(author));
        mvc.perform(
                        get("/api/v1/authors/1").content(asJsonString(author)).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(authorService, times(1)).findAuthorById(ArgumentMatchers.anyLong());
        verifyNoMoreInteractions(authorService);
    }

    @Test
    void shouldDeleteAuthorById() throws Exception {
        mvc.perform(
                        delete("/api/v1/authors/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(authorService, times(1)).deleteById(ArgumentMatchers.anyLong());
        verifyNoMoreInteractions(authorService);
    }


}