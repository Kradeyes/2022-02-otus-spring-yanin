package ru.otus.homework03.generator;

import ru.otus.homework03.domain.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorGenerator {
    public static List<Author> generateAuthorsList() {
        List<Author> authorList = new ArrayList<>();
        Author author = new Author(1, "Ivan", "Ivanov");
        authorList.add(author);
        return authorList;
    }
}
