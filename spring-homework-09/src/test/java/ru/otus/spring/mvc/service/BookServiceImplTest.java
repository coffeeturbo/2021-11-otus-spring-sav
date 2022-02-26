package ru.otus.spring.mvc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.mvc.domain.Author;
import ru.otus.spring.mvc.domain.Book;

import javax.persistence.EntityManager;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookServiceImplTest {

    @Autowired
    BookService bookService;

    @Autowired
    EntityManager entityManager;

    @Transactional
    @Test
    void createBook() {
        var firstAuthor = entityManager.find(Author.class, 1L);
        var newBook = new Book(0, firstAuthor, "Book name", Collections.emptyList());
        bookService.createBook(newBook);
        assertThat(newBook.getId()).isGreaterThan(0);
    }


    @Test
    void getAllBooks() {
        assertThat(bookService.getAllBooks())
                .isNotNull()
                .hasSize(8)
                .allMatch(book -> book.getId() != 0)
                .allMatch(book -> !book.getName().equals(""))
                .allMatch(book -> !book.getAuthor().getFirstName().equals(""));
    }

    @Test
    void getBookById() {
        assertThat(bookService.getBookById(1L))
                .isPresent()
                .get()
                .matches(book -> book.getName().equals("Мартин Иден"))
                .matches(book -> book.getId() == 1L);
    }

    @Transactional
    @Test
    void updateBook() {
        var book = entityManager.find(Book.class, 1L);
        book.setName("updatedBook");
        var updatedBook = bookService.updateBook(book);
        assertThat(updatedBook.getName()).isEqualTo("updatedBook");
    }

    @Transactional
    @Test
    void deleteBook() {
        bookService.deleteBook(1L);
        assertThat(bookService.getBookById(1L)).isNotPresent();
    }
}