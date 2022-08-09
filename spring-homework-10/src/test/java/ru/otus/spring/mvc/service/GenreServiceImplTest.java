package ru.otus.spring.mvc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.mvc.domain.Genre;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = {
        "classpath:/db.changelog/data/2022-04-02-genre.sql",
})
@SpringBootTest
class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void create() {
        var newGenre = Genre.builder().name("newGenre").build();

        var updated = genreService.create(newGenre);

        assertThat(updated.getId()).isNotZero();


    }

    @Test
    void update() {
        var newGenre = Genre.builder().id(1L).name("updatedGenre").build();

        genreService.update(newGenre);

        assertThat(entityManager.find(Genre.class, 1L))
                .isEqualTo(newGenre);

    }

    @Test
    void findAllGenres() {
        assertThat(genreService.findAllGenres())
                .hasSizeGreaterThan(0);
    }

    @Test
    void findById() {
        var genre = entityManager.find(Genre.class, 1L);
        assertThat(genreService.findById(1L))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(genre);
    }

    @Test
    void findByIds() {

        var ids = new long[]{1, 2};
        assertThat(genreService.findByIds(ids))
                .hasSize(2);
    }

    @Test
    void deleteById() {

        genreService.deleteById(1L);
        assertThat(entityManager.find(Genre.class, 1L))
                .isNull();
    }
}