package ru.otus.spring.mvc.http.controller.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.mvc.domain.Genre;
import ru.otus.spring.mvc.service.GenreService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GenreController.class)
class GenreControllerTest extends BaseApiTest {

    private static final String API_V_1_GENRE = "/api/v1/genre";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @SneakyThrows
    @Test
    void shouldCreateGenre() {

        var newGenre = Genre.builder()
                .name("Drama")
                .build();

        var genre = Genre.builder()
                .name("Drama")
                .id(1)
                .build();

        given(genreService.create(newGenre)).willReturn(genre);
        mvc.perform(
                        post(API_V_1_GENRE)
                                .content(asJsonString(newGenre))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()", is(2)));

        verify(genreService, times(1)).create(ArgumentMatchers.any(Genre.class));
        verifyNoMoreInteractions(genreService);
    }

    @SneakyThrows
    @Test
    void shouldGetAllGenres() {

        var genres = List.of(
                new Genre(1L, "dramma"),
                new Genre(2L, "commedy")
        );

        given(genreService.findAllGenres()).willReturn(genres);

        mvc.perform(
                        get(API_V_1_GENRE)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));


    }

    @SneakyThrows
    @Test
    void shouldGetGenreById() {

        var actualGenre = new Genre(1L, "dramma");

        given(genreService.findById(1)).willReturn(Optional.of(actualGenre));

        mvc.perform(
                        get(API_V_1_GENRE + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("dramma")));
    }

    @SneakyThrows
    @Test
    void shouldUpdateGenre() {

        var updateGenre = new Genre(1L, "updated dramma");
        var expectedGenre = new Genre(1L, "updated dramma");

        given(genreService.update(updateGenre)).willReturn(expectedGenre);

        mvc.perform(
                        put(API_V_1_GENRE + "/1")
                                .content(asJsonString(updateGenre))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("updated dramma")));


    }

    @SneakyThrows
    @Test
    void shouldDeleteGenre() {

        mvc.perform(
                        delete(API_V_1_GENRE + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        verify(genreService, times(1)).deleteById(ArgumentMatchers.anyLong());
        verifyNoMoreInteractions(genreService);

    }

}