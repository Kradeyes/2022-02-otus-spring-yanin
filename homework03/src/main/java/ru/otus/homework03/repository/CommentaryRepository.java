package ru.otus.homework03.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework03.domain.Commentary;

import java.util.List;
import java.util.Optional;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
    List<Commentary> findCommentariesByBook_Id(long bookId);

    void deleteById(long id);

    Optional<Commentary> findCommentaryByName(String name);
}
