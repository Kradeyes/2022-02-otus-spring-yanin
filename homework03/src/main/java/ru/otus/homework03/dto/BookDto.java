package ru.otus.homework03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    private String bookTitle;
    private String authorName;
    private String authorSurname;
    private String genreName;


    public static Book toDomainObject(BookDto bookDto) {
        return new Book(bookDto.getBookTitle(), new Author(bookDto.getAuthorName(), bookDto.getAuthorSurname()), new Genre(bookDto.getGenreName()));
    }

    public static Book toDomainObjectWithId(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getBookTitle(), new Author(bookDto.getAuthorName(), bookDto.getAuthorSurname()), new Genre(bookDto.getGenreName()));
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getBookTitle(), book.getAuthor().getName(), book.getAuthor().getSurname(), book.getGenre().getGenreName());
    }

    public BookDto(String bookTitle, String authorName, String authorSurname, String genreName) {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.genreName = genreName;
    }
}
