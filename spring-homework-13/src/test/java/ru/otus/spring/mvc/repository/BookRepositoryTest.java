package ru.otus.spring.mvc.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.mvc.domain.Author;
import ru.otus.spring.mvc.domain.Book;
import ru.otus.spring.mvc.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Книги")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private TestEntityManager entityManager;

    private Author author;

    @BeforeEach
    void init() {
        author = entityManager.find(Author.class, 1L);
    }

    @DisplayName("Получает количество книг")
    @Test
    void count() {
        assertThat(bookRepo.count()).isEqualTo(8);
    }

    @DisplayName("Создать книгу")
    @Test
    void insert() {
        var newBook = Book.builder().author(author).name("test book").build();
        newBook = bookRepo.save(newBook);
        var actualBook = entityManager.find(Book.class, newBook.getId());
        assertThat(newBook.getId()).isPositive();
        assertThat(actualBook).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newBook);
    }

    @DisplayName("Изменить книгу")
    @Test
    void update() {
        var genres = List.of(Genre.builder().id(1).name("comedy").build());

        var updateBook = Book.builder()
                .id(1)
                .author(author)
                .genres(genres)
                .name("test book")
                .build();

        updateBook = bookRepo.save(updateBook);
        var actualBook = entityManager.find(Book.class, updateBook.getId());
        assertThat(actualBook)
                .isNotNull()
                .isExactlyInstanceOf(Book.class)
                .isEqualTo(updateBook);
    }

    @Transactional
    @DisplayName("Удалить книгу по id")
    @Test
    void deleteById() {
        bookRepo.deleteById(8);
        assertThat(entityManager.find(Book.class, 8L)).isNull();
    }

    @DisplayName("Получить книгу по id")
    @Test
    void getById() {
        var expectedBook = entityManager.find(Book.class, 1L);
        assertThat(bookRepo.getById(1)).isPresent().get()
                .usingRecursiveComparison()
                .isNotNull()
                .isEqualTo(expectedBook);
    }

    @DisplayName("Получить все книги")
    @Test
    void getAll() {
        var books = bookRepo.findAll();
        assertThat(books)
                .isNotNull()
                .hasSize(8)
                .allMatch(book -> book.getId() != 0)
                .allMatch(book -> book.getAuthor() != null
                        && !book.getAuthor().getFirstName().equals(""))
                .allMatch(book -> book.getGenres().size() >= 0);
    }
}