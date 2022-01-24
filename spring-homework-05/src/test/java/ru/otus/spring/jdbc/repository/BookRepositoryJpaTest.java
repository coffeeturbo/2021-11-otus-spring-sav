package ru.otus.spring.jdbc.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(" Репозиторий Книги ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepository bookRepository;

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
        assertThat(bookRepository.count()).isEqualTo(8);
    }

    @DisplayName("Создать книгу")
    @Test
    void insert() {
        var newBook = Book.builder().author(author).name("test book").build();
        newBook = bookRepository.save(newBook);
        var actualBook = entityManager.find(Book.class, newBook.getId());
        assertThat(newBook.getId()).isNotNull().isGreaterThan(0);
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

        updateBook = bookRepository.save(updateBook);
        var actualBook = entityManager.find(Book.class, updateBook.getId());
        assertThat(actualBook)
                .isNotNull()
                .isExactlyInstanceOf(Book.class)
                .isEqualTo(updateBook);
    }

    @DisplayName("Удалить книгу по id")
    @Test
    void deleteById() {
        bookRepository.deleteById(1);
        assertThat(entityManager.find(Book.class, 1L)).isNull();
    }

    @DisplayName("Получить книгу по id")
    @Test
    void getById() {
        var expectedBook = entityManager.find(Book.class, 1L);
        assertThat(bookRepository.getById(1)).isPresent().get()
                .usingRecursiveComparison()
                .isNotNull()
                .isEqualTo(expectedBook);
    }

    @DisplayName("Получить все книги")
    @Test
    void getAll() {
        var books = bookRepository.getAll();
        assertThat(books)
                .isNotNull()
                .hasSize(8)
                .allMatch(book -> book.getId() != 0)
                .allMatch(book -> book.getAuthor() != null
                        && !book.getAuthor().getFirstName().equals(""))
                .allMatch(book -> book.getGenres().size() >= 0);
    }
}