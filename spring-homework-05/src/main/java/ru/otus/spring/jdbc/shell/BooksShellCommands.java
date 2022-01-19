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
    public String getBookById(@ShellOption({"bookId", "id"}) long bookId) {
        return bookService.getBookById(bookId);
    }

    @ShellMethod(value = "Create book", key = {"create book", "cb"})
    public String createBook(long authorId, String name, String genres) {
        return bookService.createBook(authorId, name, genres);
    }

    @ShellMethod(value = "Update book", key = {"update book", "ub"})
    public String updateBook(long bookId, long authorId, String name, String genres) {
        return bookService.updateBook(bookId, authorId, name, genres);
    }

    @ShellMethod(value = "Delete book", key = {"delete book", "db"})
    public String deleteBook(long bookId) {
        return bookService.deleteBook(bookId);
    }
}
