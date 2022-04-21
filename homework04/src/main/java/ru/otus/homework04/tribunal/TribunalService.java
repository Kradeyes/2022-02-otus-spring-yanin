package ru.otus.homework04.tribunal;

import org.springframework.stereotype.Service;
import ru.otus.homework04.domain.Defendant;
import ru.otus.homework04.domain.Judgment;

@Service
public class TribunalService {

    public Judgment sentence(Defendant defendant) throws Exception {
        System.out.println("Making a judgment for " + defendant.getDefendantName());
        Thread.sleep(3000);
        System.out.println("Sentencing for " + defendant.getDefendantName() + " completed");
        return new Judgment(defendant.getDefendantName());
    }
}
