package ru.otus.spring.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Column(name = "text", nullable = false)
    private String text;

    @Override
    public String toString() {
        return "Comment{"
                + "id=" + id
                + ", text='" + text
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ru.otus.spring.mvc.domain.Comment comment = (ru.otus.spring.mvc.domain.Comment) o;
        return id == comment.id && text.equals(comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}
