package ru.otus.spring.mvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.mvc.domain.Book;
import ru.otus.spring.mvc.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Transactional
    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(long bookId) {
        bookRepository.deleteById(bookId);
    }
}
