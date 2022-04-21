package ru.otus.homework04;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework04.domain.Defendant;
import ru.otus.homework04.domain.Judgment;

@MessagingGateway
public interface Tribunal {

    @Gateway(requestChannel = "defendantsChannel", replyChannel = "judgmentChannel")
    Judgment process(Defendant Defendant);
}
