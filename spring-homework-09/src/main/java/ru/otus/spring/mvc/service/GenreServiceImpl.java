package ru.otus.spring.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.mvc.domain.Genre;
import ru.otus.spring.mvc.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return genreRepository.findById(id);
    }

    @Override
    public List<Genre> findByIds(long[] ids) {

        var longs = Arrays.stream(ids).boxed().collect(Collectors.toList());
        return genreRepository.findAllById(longs);
    }
}
