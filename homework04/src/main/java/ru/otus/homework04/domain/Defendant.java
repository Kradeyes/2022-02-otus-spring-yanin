package ru.otus.homework04.domain;

public class Defendant {
    private final String defendantName;

    public Defendant(String defendantName) {
        this.defendantName = defendantName;
    }

    public String getDefendantName() {
        return defendantName;
    }
}
