package ru.otus.homework03.generator;

import ru.otus.homework03.domain.Commentary;

import java.util.ArrayList;
import java.util.List;

public class CommentaryGenerator {
    public static Commentary generateCommentary() {
        Commentary commentary = new Commentary();
        commentary.setName("good comment");
        commentary.setContent("good content");
        return commentary;
    }

    public static Commentary generateCommentaryForDaoTests() {
        Commentary commentary = new Commentary();
        commentary.setName("good comment");
        commentary.setContent("good content");
        commentary.setBook(BookGenerator.generateBookWithIdForAll());
        return commentary;
    }

    public static List<Commentary> generateCommentaryList() {
        List<Commentary> commentaryList = new ArrayList<>();
        Commentary commentary = new Commentary(1, "good comment", "good content", BookGenerator.generateBookWithIdForAll());
        commentaryList.add(commentary);
        return commentaryList;
    }
}
