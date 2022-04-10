package ru.otus.homework03.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework03.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByNameAndSurname(String name, String surname);

    void deleteById(long id);
}
