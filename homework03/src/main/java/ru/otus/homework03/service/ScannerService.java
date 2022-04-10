package ru.otus.homework03.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ScannerService {
    private final Scanner scanner;

    public ScannerService() {
        this.scanner = new Scanner(System.in);
    }

    public String userInput() {
        return scanner.nextLine();
    }
}
