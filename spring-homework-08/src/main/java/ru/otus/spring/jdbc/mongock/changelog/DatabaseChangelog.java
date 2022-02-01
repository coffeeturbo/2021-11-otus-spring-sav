package ru.otus.spring.jdbc.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Comment;
import ru.otus.spring.jdbc.domain.Genre;
import ru.otus.spring.jdbc.repository.AuthorRepository;
import ru.otus.spring.jdbc.repository.BookRepository;
import ru.otus.spring.jdbc.repository.CommentRepository;
import ru.otus.spring.jdbc.repository.GenreRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "vsavushkin", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }


    @ChangeSet(order = "002", id = "insert authors", author = "vsavushkin", runAlways = true)
    public void insertAuthors(AuthorRepository repository, GenreRepository genreRepository) {
        var authors = List.of(
                new Author("1", "Jack", "London"),
                new Author("2", "Isiac", "Asimov"),
                new Author("3", "Carl", "Marks")
        );
        repository.saveAll(authors);
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "vsavushkin", runAlways = true)
    public void insertGenres(GenreRepository genreRepository) {
        var genres = List.of(
                Genre.builder().id("1").name("comedy").build(),
                Genre.builder().id("2").name("drama").build(),
                Genre.builder().id("3").name("science").build()
        );

        genreRepository.saveAll(genres);
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "vsavushkin", runAlways = true)
    public void insertBooks(BookRepository bookRepository,
                            AuthorRepository authorRepository,
                            GenreRepository genreRepository
    ) {
        var jackLondon = authorRepository
                .findOneByFirstNameAndLastName("Jack", "London")
                .orElseThrow();

        var isacAsimov = authorRepository
                .findOneByFirstNameAndLastName("Isiac", "Asimov")
                .orElseThrow();

        var genres = genreRepository.findAll();

        var books = List.of(
                Book.builder().id("1").name("Мартин Иден").author(jackLondon).genres(genres.subList(0, 1)).build(),
                Book.builder().name("Любовь к жизни").author(jackLondon).genres(genres.subList(0, 1)).build(),
                Book.builder().name("Сами боги").author(isacAsimov).genres(genres.subList(1, 1)).build(),
                Book.builder().name("Основание").author(isacAsimov).genres(genres.subList(1, 1)).build(),
                Book.builder().name("Стальные пещеры").author(isacAsimov).genres(genres).build()
        );

        bookRepository.saveAll(books);
    }

    @ChangeSet(order = "005", id = "insertComments", author = "vsavushkin", runAlways = true)
    public void insertComments(CommentRepository commentRepository, BookRepository bookRepository) {

        var books = bookRepository.findAll();
        var comments = List.of(
                Comment.builder().id("1").book(books.get(1)).text("Ужасная книга читал до упада").build(),
                Comment.builder().id("2").book(books.get(1)).text("не читал").build(),
                Comment.builder().id("3").book(books.get(1)).text("плохая книга").build(),
                Comment.builder().id("4").book(books.get(1)).text("Так себе").build(),
                Comment.builder().id("5").book(books.get(2)).text("читать не буду").build(),
                Comment.builder().id("6").book(books.get(3)).text("Отличная книга читал до упада").build()
        );

        commentRepository.saveAll(comments);
    }
}
