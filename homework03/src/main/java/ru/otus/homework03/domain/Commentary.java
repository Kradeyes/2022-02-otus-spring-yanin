package ru.otus.homework03.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commentaries")
@NamedEntityGraph(name = "otus-commentary-books-entity-graph",
        attributeNodes = {@NamedAttributeNode("book")})
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @ManyToOne(targetEntity = Book.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id")
    private Book book;
}
