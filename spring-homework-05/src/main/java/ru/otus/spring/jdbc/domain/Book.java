package ru.otus.spring.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NamedEntityGraph(name = "author-entity-graph",
        attributeNodes = {
        @NamedAttributeNode("author")
})
@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Author author;

    @Column(name = "name", nullable = false)
    private String name;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @BatchSize(size = 10)
    @OneToMany(targetEntity = Comment.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "book"
    )
    private List<Comment> comments;
}
