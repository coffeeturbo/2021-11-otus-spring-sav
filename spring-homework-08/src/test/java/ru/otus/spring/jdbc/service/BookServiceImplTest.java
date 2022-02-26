package ru.otus.spring.jdbc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Comment;
import ru.otus.spring.jdbc.domain.Genre;
import ru.otus.spring.jdbc.repository.CommentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;


@DisplayName("Сервис Книг")
@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate template;

    private Author authorJackLondon;

    private List<Genre> genreList;

    @BeforeEach
    void init() {
        Query query = new Query(
                Criteria.where("firstName").is("Jack")
                        .and("lastName").is("London")
        );
        authorJackLondon = template.findOne(query, Author.class);
        genreList = template.findAll(Genre.class);
    }


    @Test
    void contextLoads() {
        assertThat(bookService).isNotNull();
        assertThat(template).isNotNull();
    }

    @Test
    void createBook() {
        var bookGenres = implode(genreList.subList(0, 2));

        var result = bookService.save(null, authorJackLondon.getId(), "testBook", bookGenres);
        assertThat(result).contains("AuthorName: JackLondon, NAME: testBook, GENRES: comedy, drama");
    }

    @Test
    void getAllBooks() {
        var books = bookService.getAllBooks();
        assertThat(books)
                .isNotNull()
                .contains("AuthorName: JackLondon, NAME: Мартин Иден, GENRES: comedy;")
                .contains("AuthorName: JackLondon, NAME: Любовь к жизни, GENRES: comedy;");
    }

    @Test
    void getBookById() {
        Query query = new Query(
                Criteria.where("name").is("Мартин Иден")
        );
        var book = template.findOne(query, Book.class);
        var actual = bookService.getBookById(book.getId());
        assertThat(actual).contains(", AuthorName: JackLondon, NAME: Мартин Иден, GENRES: comedy");
    }

    @Test
    void updateBook() {
    }

    @Test
    void deleteBook() {

        var bookGenres = implode(genreList.subList(0, 2));

        bookService.save(null, authorJackLondon.getId(), "testBookDelete", bookGenres);

        var book = template.findOne(new Query(Criteria.where("name").is("testBookDelete")), Book.class);
        assertThat(book).isNotNull();
        assertThat(bookService.deleteBook(book.getId()))
                .isNotNull()
                        .contains(String.format("Book %s was deleted", book.getId()));

        assertThatCode(() -> bookService.getBookById(book.getId()))
                .isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteBookWithComments() {

        var bookGenres = implode(genreList.subList(0, 2));
        bookService.save(null, authorJackLondon.getId(), "testBookDelete", bookGenres);
        var book = template.findOne(new Query(Criteria.where("name").is("testBookDelete")), Book.class);
        assertThat(book).isNotNull();

        commentRepository.saveAll(List.of(
                new Comment(null, book, "test1"),
                new Comment(null, book, "test1333")
        ));

        var bookCommentsBefore = template.find(
                new Query(Criteria.where("book.id").is(book.getId())),
                Comment.class);

        assertThat(bookCommentsBefore).hasSize(2);
        assertThat(bookService.deleteBook(book.getId()))
                .isNotNull()
                        .contains(String.format("Book %s was deleted", book.getId()));
        assertThatCode(() -> bookService.getBookById(book.getId()))
                .isExactlyInstanceOf(NoSuchElementException.class);

        var bookCommentsAfter = template.find(
                new Query(Criteria.where("book.id").is(book.getId())),
                Comment.class);
        assertThat(bookCommentsAfter).hasSize(0);
    }

    private String implode(List<Genre> genres) {
        return genres.stream()
                .map(Genre::getId)
                .collect(Collectors.joining(","));
    }
}