package com.senlainternship.logger.config;

import com.senlainternship.logger.model.LogMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, LogMessage> kafkaTemplate;

    private final String topicMessage;

    private final String topicException;

    public void sendMessage(LogMessage message) {
        kafkaTemplate.send(topicMessage, message);
    }

    public void sendException(LogMessage message) {
        kafkaTemplate.send(topicException, message);
    }
}
