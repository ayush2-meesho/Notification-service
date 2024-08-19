package com.messagingservice.notificationservice.listener;


import com.messagingservice.notificationservice.db.mysql.repository.SmsRepository;
import com.messagingservice.notificationservice.models.entity.mysql.SmsEntity;
import com.messagingservice.notificationservice.services.SmsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaListenerService {

    private SmsService smsService;

    private SmsRepository smsRepository;

    @KafkaListener(topics = "notification", groupId = "notification-consumer")
    public void listen(Long message) throws Exception {
        log.info("Recieved notification message: {}", message);
        Optional<SmsEntity> smsEntityOptional = smsRepository.findById(message);
        // send sms to third party api
        smsRepository.updateStatusById(message, "sent", "pending");
        smsService.saveSmsToES(smsEntityOptional.get());
    }
}
