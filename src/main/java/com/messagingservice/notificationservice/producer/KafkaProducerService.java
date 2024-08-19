package com.messagingservice.notificationservice.producer;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private final KafkaTemplate<String, Long> kafkaTemplate;

    public void sendMessage(String topic, Long message) {
        logger.info("Sending message: '{}'to topic: '{}'", message, topic);
        try {
            kafkaTemplate.send(topic, message);
        }
        catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
}
