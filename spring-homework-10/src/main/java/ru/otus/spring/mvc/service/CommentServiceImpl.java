package ru.otus.spring.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.mvc.domain.Comment;
import ru.otus.spring.mvc.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Override
    public Comment createComment(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return repository.findAll();
    }

    @Override
    public Optional<Comment> getCommentById(long commentId) {
        return repository.findById(commentId);
    }

    @Override
    public void deleteById(long commentId) {
        repository.deleteById(commentId);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return repository.save(comment);
    }
}
