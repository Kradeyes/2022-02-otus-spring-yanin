package ru.otus.homework02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.Student;

import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class StudentTestStartServiceImpl implements StudentTestStartService {
    private final StudentTestServiceImpl studentTestService;

    @Override
    public void start(MessageSource messageSource) {
        Scanner in = new Scanner(System.in);
        System.out.println("Select a language from the suggested ones (RU / FR) or set the default option (US)");
        String locale = in.next();
        String selectedLocale = chooseLocale(locale);
        System.out.println(messageSource.getMessage("main.selectedLanguage", null, Locale.forLanguageTag(selectedLocale)));
        System.out.println(messageSource.getMessage("main.enterYourName", null, Locale.forLanguageTag(selectedLocale)));
        String name = in.next();
        System.out.println(messageSource.getMessage("main.enterYourSurname", null, Locale.forLanguageTag(selectedLocale)));
        String surname = in.next();
        Question question = studentTestService.generateQuestion(selectedLocale);
        Student student = studentTestService.setStudentNameAndSurname(name, surname);
        for (String s : question.getQuestionsWithAnswers().keySet()) {
            System.out.println(s);
            System.out.println(question.getQuestionsWithAnswers().get(s));
            String answer = in.next();
            studentTestService.checkStudentAnswer(answer, student, selectedLocale);
        }
        System.out.println(student.getSurname() + " "
                + student.getName() + " "
                + messageSource.getMessage("main.testResult", null, Locale.forLanguageTag(selectedLocale)) + " "
                + studentTestService.getStudentScore(student));
        in.close();
    }

    private String chooseLocale(String locale) {
        Locale.setDefault(new Locale("en", "US"));
        String selectedLocale = Locale.getDefault().getLanguage();
        if (createLocaleMap().get(locale) != null && !createLocaleMap().get(locale).isEmpty()) {
            selectedLocale = createLocaleMap().get(locale);
        }
        return selectedLocale;
    }

    private HashMap<String, String> createLocaleMap() {
        HashMap<String, String> localeMap = new HashMap<>();
        localeMap.put("RU", "ru-RU");
        localeMap.put("FR", "fr-FR");
        return localeMap;
    }
}
