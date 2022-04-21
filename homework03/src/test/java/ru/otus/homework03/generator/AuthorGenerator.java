package ru.otus.homework03.generator;

import ru.otus.homework03.domain.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorGenerator {
    public static Author generateAuthor() {
        Author author = new Author();
        author.setName("Ivan");
        author.setSurname("Ivanov");
        return author;
    }

    public static List<Author> generateAuthorsList() {
        List<Author> authorList = new ArrayList<>();
        Author author = new Author(1, "Ivan", "Ivanov");
        authorList.add(author);
        return authorList;
    }

    public static Optional<Author> generateOptionalAuthor() {
        Author author = new Author(1, "Ivan", "Ivanov");
        return Optional.of(author);
    }
}
