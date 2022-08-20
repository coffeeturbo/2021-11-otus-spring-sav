package ru.otus.spring.mvc.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.mvc.domain.Book;
import ru.otus.spring.mvc.dto.BookDto;
import ru.otus.spring.mvc.service.AuthorService;
import ru.otus.spring.mvc.service.GenreService;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorService authorService;
    private final GenreService genreService;


    public Book toDomainObject(BookDto bookDto) {
        var book = bookDto.toDomainObject();
        var author = authorService.findAuthorById(bookDto.getAuthorId()).orElseThrow();

        var genres = genreService.findByIds(bookDto.getBookGenreIds());

        book.setAuthor(author);
        book.setGenres(genres);

        return book;
    }
}
