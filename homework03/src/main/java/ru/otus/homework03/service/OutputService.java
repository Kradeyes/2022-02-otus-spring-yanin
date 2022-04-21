package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OutputService {
    private final MessageSource messageSource;

    public void genreNameOutput() {
        System.out.println(messageSource.getMessage("shell.genreName", null, Locale.getDefault()));
    }

    public void allGenresOutput() {
        System.out.println(messageSource.getMessage("shell.showAllGenres", null, Locale.getDefault()));
    }

    public void authorNameOutput() {
        System.out.println(messageSource.getMessage("shell.authorName", null, Locale.getDefault()));
    }

    public void authorSurnameOutput() {
        System.out.println(messageSource.getMessage("shell.authorSurname", null, Locale.getDefault()));
    }

    public void allAuthorsOutput() {
        System.out.println(messageSource.getMessage("shell.showAllAuthors", null, Locale.getDefault()));
    }

    public void bookTitleOutput() {
        System.out.println(messageSource.getMessage("shell.bookTitle", null, Locale.getDefault()));
    }

    public void allBooksOutput() {
        System.out.println(messageSource.getMessage("shell.showAllBooks", null, Locale.getDefault()));
    }

    public void commentaryNameOutput() {
        System.out.println(messageSource.getMessage("shell.commentaryName", null, Locale.getDefault()));
    }

    public void commentaryContentOutput() {
        System.out.println(messageSource.getMessage("shell.commentaryContent", null, Locale.getDefault()));
    }

    public void allCommentariesOutput() {
        System.out.println(messageSource.getMessage("shell.showAllCommentaries", null, Locale.getDefault()));
    }
}
