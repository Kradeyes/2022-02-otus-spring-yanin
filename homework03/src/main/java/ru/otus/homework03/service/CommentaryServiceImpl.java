package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework03.repository.CommentaryRepository;
import ru.otus.homework03.domain.Commentary;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentaryServiceImpl implements CommentaryService {
    private final CommentaryRepository commentaryRepository;

    @Override
    public void createNewCommentary(Commentary commentary) {
        if (!checkTheExistenceOfTheCommentary(commentary.getName())) {
            commentaryRepository.save(commentary);
        }
    }

    @Override
    public long getIdByCommentaryName(String name) {
        long id = 0;
        Optional<Commentary> commentary = commentaryRepository.findCommentaryByName(name);
        if (commentary.isPresent()) {
            id = commentary.get().getId();
        }
        return id;
    }

    @Override
    public void deleteCommentary(long commentaryId) {
        commentaryRepository.deleteById(commentaryId);
    }

    @Override
    public List<Commentary> getAllCommentariesByBookId(long bookId) {
        return commentaryRepository.findCommentariesByBook_Id(bookId);
    }

    private boolean checkTheExistenceOfTheCommentary(String name) {
        Optional<Commentary> commentary = commentaryRepository.findCommentaryByName(name);
        return commentary.isPresent();
    }
}
