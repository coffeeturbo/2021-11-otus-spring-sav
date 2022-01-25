package ru.otus.spring.jdbc.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.jdbc.service.CommentService;

@RequiredArgsConstructor
@ShellComponent
public class CommentShellCommands {
    private final CommentService commentService;

    @ShellMethod(value = "List all comments", key = {"list comment", "lc"})
    public String listComments() {
        return commentService.getAllComments();
    }

    @ShellMethod(value = "Get comment by id", key = {"get comment", "gc"})
    public String getcommentById(@ShellOption({"commentId", "id"}) long commentId) {
        return commentService.getCommentById(commentId);
    }

    @ShellMethod(value = "Create comment", key = {"create comment", "cc"})
    public String createComent(long bookId, String text) {
        return commentService.createComment(bookId, text);
    }

    @ShellMethod(value = "Update comment", key = {"update comment", "uc"})
    public String updateComment(long commentId, long bookId, String text) {
        return commentService.updateComment(commentId, bookId, text);
    }

    @ShellMethod(value = "Delete comment", key = {"delete comment", "dc"})
    public String deleteComment(long commentId) {
        return commentService.deleteById(commentId);
    }
}
