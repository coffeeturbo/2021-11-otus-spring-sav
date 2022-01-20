package ru.otus.spring.jdbc.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(" Сервис Книг ")
@Transactional
@SpringBootTest
class BookServiceImplTest {

    @Autowired
    BookService bookService;

    @DisplayName(" Создать новую книгу ")
    @Rollback
    @Test
    void createBook() {
        var result = bookService.createBook(1, "testBook", "1,2");
        assertThat(result).contains("AuthorName: JackLondon, NAME: testBook, GENRES: comedy, drama");
    }

    @DisplayName(" Создать новую книгу c жанрами ")
    @Rollback
    @Test
    void createBookWithGenres() {
        var result = bookService.createBook(1, "testBook", "1,2");
        assertThat(result).contains("AuthorName: JackLondon, NAME: testBook, GENRES: comedy, drama");
    }

    @DisplayName(" Получить все книги ")
    @Test
    void getAllBooks() {
        var books = bookService.getAllBooks();
        assertThat(books)
                .contains(List.of(
                        "ID: 1, AuthorName: JackLondon, NAME: Мартин Иден, GENRES: comedy, drama;",
                                " ID: 2, AuthorName: JackLondon, NAME: Любовь к жизни, GENRES:"
                ));
    }

    @DisplayName(" Получить книгу по id ")
    @Test
    void getBookById() {
        var book = bookService.getBookById(1);
        assertThat(book).isEqualTo("ID: 1, AuthorName: JackLondon, NAME: Мартин Иден, GENRES: comedy, drama");
    }

    @DisplayName(" Изменить книгу ")
    @Rollback
    @Test
    void updateBook() {
        var book = bookService.updateBook(1, 1, "updatedBook", "1,2");
        assertThat(book).isEqualTo("ID: 1, AuthorName: JackLondon, NAME: updatedBook, GENRES: comedy, drama");
    }

    @DisplayName(" Удалить книгу ")
    @Rollback
    @Test
    void deleteBook() {
        var result = bookService.deleteBook(1);
        assertThat(result).isEqualTo("Book 1 was deleted");
    }
}