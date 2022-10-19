package ru.otus.spring.jdbc.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.jdbc.service.BookService;


@RequiredArgsConstructor
@ShellComponent
public class BooksShellCommands {
    private final BookService bookService;


    @ShellMethod(value = "List all Books", key = {"list book", "lb"})
    public String listBooks() {
        return bookService.getAllBooks();
    }

    @ShellMethod(value = "Get book by id", key = {"get book", "gb"})
    public String getBookById(@ShellOption({"bookId", "id"}) String bookId) {
        return bookService.getBookById(bookId);
    }

    @ShellMethod(value = "Create book", key = {"create book", "cb"})
    public String createBook(String authorId, String name, String genres) {
        return bookService.save(null, authorId, name, genres);
    }

    @ShellMethod(value = "Update book", key = {"update book", "ub"})
    public String updateBook(String bookId, String authorId, String name, String genres) {
        return bookService.save(bookId, authorId, name, genres);
    }

    @ShellMethod(value = "Delete book", key = {"delete book", "db"})
    public String deleteBook(String bookId) {
        return bookService.deleteBook(bookId);
    }

}
