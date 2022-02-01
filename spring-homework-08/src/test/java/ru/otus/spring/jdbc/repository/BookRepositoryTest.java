package ru.otus.spring.jdbc.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Репозиторий Книг")
@DataMongoTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private MongoTemplate template;

    private Author authorIsacAsimov, authorJackLondon;
    private List<Genre> genreList;

    @BeforeEach
    void init() {
        Query query = new Query(
                Criteria.where("firstName").is("Jack")
                        .and("lastName").is("London")
        );
        authorJackLondon = template.findOne(query, Author.class);

        Query query2 = new Query(
                Criteria.where("firstName").is("Isiac")
                        .and("lastName").is("Asimov")
        );
        authorIsacAsimov = template.findOne(query2, Author.class);

        genreList = template.findAll(Genre.class);
    }

    @DisplayName("Получает количество книг")
    @Test
    void count() {
        assertThat(bookRepo.count()).isEqualTo(5);
    }

    @DisplayName("Создать книгу")
    @Test
    void insert() {
        var newBook = Book.builder()
                .author(authorJackLondon)
                .name("test book")
                .genres(genreList)
        .build();
        newBook = bookRepo.save(newBook);
        var actualBook = template.findById(newBook.getId(), Book.class);
        assertThat(newBook.getId()).isNotNull();

        assertThat(newBook.getGenres())
                .isNotNull()
                .hasSize(3)
                .allMatch(genre -> !genre.getName().equals(""))
                .allMatch(genre -> !genre.getId().equals(""));

        assertThat(actualBook).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newBook);

        bookRepo.delete(newBook);
    }

    @DisplayName("Изменить книгу")
    @Test
    void update() {
        var newBook = Book.builder()
                .author(authorIsacAsimov).name("test book")
                .build();

        newBook = bookRepo.save(newBook);

        var updateBook = bookRepo.findById(newBook.getId()).orElseThrow();

        updateBook.setName("update name");

        updateBook.setAuthor(authorJackLondon);
        updateBook.setGenres(List.of(genreList.get(1)));
        bookRepo.save(updateBook);

        var actualBook = template.findById(updateBook.getId(), Book.class);
        assertThat(actualBook)
                .isNotNull()
                .isExactlyInstanceOf(Book.class)
                .matches(book -> book.getName().equals("update name"))
                .matches(book -> book.getAuthor().equals(authorJackLondon));

        assertThat(updateBook.getGenres())
                .isNotNull()
                .hasSize(1)
                .allMatch(genre -> !genre.getName().equals(""))
                .allMatch(genre -> !genre.getId().equals(""));

        bookRepo.delete(updateBook);
    }

    @DisplayName("Удалить книгу по id")
    @Test
    void deleteById() {
        var deleteBook = Book.builder()
                .author(authorIsacAsimov).name("test book")
                .build();

        deleteBook = bookRepo.save(deleteBook);
        bookRepo.deleteById(deleteBook.getId());
        assertThat(template.findById(deleteBook.getId(), Book.class)).isNull();
    }

    @DisplayName("Получить книгу по id")
    @Test
    void getById() {
        var newBook = Book.builder()
                .author(authorJackLondon)
                .name("getByIdName")
                .genres(genreList.subList(0, 1))
        .build();
        newBook = bookRepo.save(newBook);

        assertThat(bookRepo.findById(newBook.getId()))
                .isPresent().get()
                .isNotNull()
                .matches(book -> !book.getId().equals(""))
                .matches(book -> book.getName().equals("getByIdName"))
                .matches(book -> book.getAuthor().equals(authorJackLondon))
                .matches(book -> book.getGenres() != null
                && book.getGenres().size() > 0);

        bookRepo.delete(newBook);
    }

    @DisplayName("Получить все книги")
    @Test
    void getAll() {
        var books = bookRepo.findAll();
        assertThat(books)
                .isNotNull()
                .hasSize(5)
                .allMatch(book -> !book.getId().equals(""))
                .allMatch(book -> book.getAuthor() != null
                        && !book.getAuthor().getFirstName().equals(""))
                .allMatch(book -> book.getGenres() != null);
    }
}