package ru.otus.spring.jdbc.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.jdbc.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий жанров")
@DataMongoTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @DisplayName("Получить количество жанров")
    @Test
    void count() {
        List<Genre> genres = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            genres.add(new Genre(null, "test genre " + i));
        }
        genreRepository.saveAll(genres);

        assertThat(genreRepository.count()).isEqualTo(8);

        genreRepository.deleteAll(genres);
        assertThat(genreRepository.count()).isEqualTo(3);
    }

    @DisplayName("Создать жанр")
    @Test
    void insert() {
        var newGenre = Genre.builder().name("test genre").build();
        var genre = genreRepository.save(newGenre);
        assertThat(genre.getId()).isNotNull();

        genreRepository.delete(genre);
    }

    @DisplayName("Обновить жанр")
    @Test
    void update() {
        var updateGenre = Genre.builder().name("test genre").build();
        var genre = genreRepository.save(updateGenre);

        var expectedGenre = mongoTemplate.findById(genre.getId(), Genre.class);
        assertThat(genre).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);

        genreRepository.delete(updateGenre);
    }

    @DisplayName("Удалить жанр по id")
    @Test
    void deleteById() {
        var updateGenre = Genre.builder().name("test genre").build();
        var genre = genreRepository.save(updateGenre);

        genreRepository.deleteById(genre.getId());
        var expectedGenre = mongoTemplate.findById(genre.getId(), Genre.class);
        assertThat(expectedGenre).isNull();
    }

    @DisplayName("Получить жанр по id")
    @Test
    void getById() {
        var newGenre = Genre.builder().name("test genre").build();
        var genre = genreRepository.save(newGenre);
        assertThat(genre.getId()).isNotNull();

        var expectedGenre = mongoTemplate.findById(genre.getId(), Genre.class);
        assertThat(genreRepository.findById(genre.getId()))
                .isPresent().get()
                .isEqualTo(expectedGenre);

        genreRepository.deleteById(genre.getId());
    }

    @DisplayName("Получить все жанры ")
    @Test
    void getAll() {
        assertThat(genreRepository.findAll())
                .isNotNull()
                .hasSize(3)
                .allMatch(Objects::nonNull)
                .allMatch(genre -> !genre.getId().equals(""))
                .allMatch(genre -> !genre.getName().equals(""));
    }
}