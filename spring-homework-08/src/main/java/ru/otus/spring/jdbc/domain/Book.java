package ru.otus.spring.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;


@Document(collection = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Book {
    @Id
    private String id;

    private Author author;

    private String name;

    private List<Genre> genres;

    @Override
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", name='" + name + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return id == book.id && name.equals(book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
