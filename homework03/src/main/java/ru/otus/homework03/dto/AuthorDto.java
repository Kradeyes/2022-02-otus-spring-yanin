package ru.otus.homework03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework03.domain.Author;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private long id;
    private String name;
    private String surname;

    public static Author toDomainObject(AuthorDto authorDto) {
        return new Author(authorDto.getName(), authorDto.getSurname());
    }

    public static Author toDomainObjectWithId(AuthorDto authorDto) {
        return new Author(authorDto.getId(), authorDto.getName(), authorDto.getSurname());
    }

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName(), author.getSurname());
    }
}
