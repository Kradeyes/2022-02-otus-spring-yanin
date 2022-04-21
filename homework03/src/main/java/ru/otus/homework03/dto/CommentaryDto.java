package ru.otus.homework03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Commentary;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentaryDto {
    private long id;
    private long bookId;
    private String name;
    private String content;

    public static Commentary toDomainObject(CommentaryDto commentaryDto, Book book) {
        return new Commentary(commentaryDto.getName(), commentaryDto.getContent(), book);
    }

    public static Commentary toDomainObjectWithId(CommentaryDto commentaryDto, Book book) {
        return new Commentary(commentaryDto.getId(), commentaryDto.getName(), commentaryDto.getContent(), book);
    }

    public static CommentaryDto toDto(Commentary commentary) {
        return new CommentaryDto(commentary.getId(), commentary.getBook().getId(), commentary.getName(), commentary.getContent());
    }

    public CommentaryDto(long bookId, String name, String content) {
        this.bookId = bookId;
        this.name = name;
        this.content = content;
    }
}
