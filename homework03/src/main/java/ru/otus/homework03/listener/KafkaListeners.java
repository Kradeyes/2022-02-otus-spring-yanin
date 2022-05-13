package ru.otus.homework03.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "test", groupId = "testGroupId")
    void listener(String message) {
        System.out.println("Listener received " + message);
    }
}
