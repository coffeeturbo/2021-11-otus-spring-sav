package ru.otus.spring.jdbc.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис Книг")
@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @DisplayName("Создать новую книгу")
    @Test
    void createBook() {
        var result = bookService.createBook(1, "testBook", "1,2");
        assertThat(result).contains("AuthorName: JackLondon, NAME: testBook, GENRES: comedy, drama");
    }

    @DisplayName("Создать новую книгу c жанрами")
    @Test
    void createBookWithGenres() {
        var result = bookService.createBook(1, "testBook", "1,2");
        assertThat(result).contains("AuthorName: JackLondon, NAME: testBook, GENRES: comedy, drama");
    }

    @DisplayName("Получить все книги")
    @Test
    void getAllBooks() {
        var books = bookService.getAllBooks();
        assertThat(books)
                .contains(List.of(
                        "ID: 1, AuthorName: JackLondon, NAME: Мартин Иден, GENRES: comedy, drama;",
                                " ID: 2, AuthorName: JackLondon, NAME: Любовь к жизни, GENRES:"
                ));
    }

    @DisplayName("Получить книгу по id")
    @Test
    void getBookById() {
        var book = bookService.getBookById(6);
        assertThat(book).isEqualTo("ID: 6, AuthorName: CarlMarks, NAME: Капитал, GENRES: science");
    }

    @DisplayName("Изменить книгу")
    @Test
    void updateBook() {
        var book = bookService.updateBook(8, 1, "updatedBook", "1,2");
        assertThat(book).isEqualTo("ID: 8, AuthorName: JackLondon, NAME: updatedBook, GENRES: comedy, drama");
    }

    @Transactional
    @DisplayName("Удалить книгу")
    @Test
    void deleteBook() {
        var result = bookService.deleteBook(9);
        assertThat(result).isEqualTo("Book 9 was deleted");
    }
}