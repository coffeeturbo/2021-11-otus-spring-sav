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
                new Genre("1", "comedy"),
                new Genre("2", "drama"),
                new Genre("3", "science")
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
                new Book("1", jackLondon, "Мартин Иден", genres.subList(0, 1)),
                new Book(null, jackLondon, "Любовь к жизни", genres.subList(0, 1)),
                new Book(null, isacAsimov, "Сами боги", genres.subList(1, 1)),
                new Book(null, isacAsimov, "Основание", genres.subList(1, 1)),
                new Book(null, isacAsimov, "Стальные пещеры", genres)
        );

        bookRepository.saveAll(books);
    }

    @ChangeSet(order = "005", id = "insertComments", author = "vsavushkin", runAlways = true)
    public void insertComments(CommentRepository commentRepository, BookRepository bookRepository) {

        var books = bookRepository.findAll();
        var comments = List.of(
                new Comment("1", books.get(1), "Ужасная книга читал до упада"),
                new Comment("2", books.get(1), "не читал"),
                new Comment("3", books.get(1), "плохая книга"),
                new Comment("4", books.get(1), "Так себе"),
                new Comment("5", books.get(2), "читать не буду"),
                new Comment("6", books.get(3), "Отличная книга читал до упада")
        );

        commentRepository.saveAll(comments);
    }
}
