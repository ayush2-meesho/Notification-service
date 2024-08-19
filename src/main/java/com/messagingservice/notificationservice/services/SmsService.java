package com.messagingservice.notificationservice.services;

import com.messagingservice.notificationservice.db.elasticsearch.SmsESRepository;
import com.messagingservice.notificationservice.db.mysql.repository.SmsRepository;
import com.messagingservice.notificationservice.models.entity.elasticsearch.ESSmsDocument;
import com.messagingservice.notificationservice.models.entity.mysql.SmsEntity;
import com.messagingservice.notificationservice.models.request.GetSmsContainingTextRequest;
import com.messagingservice.notificationservice.models.request.GetSmsSentBetweenRequest;
import com.messagingservice.notificationservice.models.request.SendSmsRequest;
import com.messagingservice.notificationservice.models.response.GetSmsContainingTextResponse;
import com.messagingservice.notificationservice.models.response.GetSmsDetailsResponse;
import com.messagingservice.notificationservice.models.response.GetSmsSentBetweenResponse;
import com.messagingservice.notificationservice.models.response.SendSmsResponse;
import com.messagingservice.notificationservice.producer.KafkaProducerService;
import com.messagingservice.notificationservice.transformer.SmsTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SmsService {

    private final SmsRepository smsRepository;

    private final SmsESRepository smsESRepository;

    private final KafkaProducerService kafkaProducerService;


    public SendSmsResponse saveSmsRequest(SendSmsRequest sendSmsRequest) throws Exception{
        SmsEntity smsEntity = SmsTransformer.getSmsEntity(sendSmsRequest);
        smsEntity = smsRepository.save(smsEntity);
        log.info("saved sms to mysql: {}", smsEntity);
        kafkaProducerService.sendMessage("notification", smsEntity.getId());
        log.info("message produced in kafka: {}", smsEntity);
        return SmsTransformer.getSendSmsResponse(smsEntity);
    }

    public GetSmsDetailsResponse getRequestDetails(Long id) throws Exception{
        Optional<SmsEntity> smsEntityOptional = smsRepository.findById(id);
        if(smsEntityOptional.isPresent()) {
            return GetSmsDetailsResponse.builder().smsEntity(smsEntityOptional.get()).build();
        } else {
            return GetSmsDetailsResponse.builder().smsEntity(null).build();
        }
    }

    public ESSmsDocument saveSmsToES(SmsEntity smsEntity) throws Exception {
        ESSmsDocument smsDocument = SmsTransformer.getESSmsDocument(smsEntity);
        smsDocument = smsESRepository.save(smsDocument);
        log.info("Saved sms in elasticsearch: {}", smsDocument);
        return smsDocument;
    }

    public GetSmsSentBetweenResponse getSentBetweenDetails(GetSmsSentBetweenRequest getSmsSentBetweenRequest) throws Exception{
        Pageable pageable = PageRequest.of(getSmsSentBetweenRequest.getPageNumber(), getSmsSentBetweenRequest.getSize());
        ZoneId zoneId = ZoneId.systemDefault();
        // Convert LocalDateTime to epochmilli
        List<ESSmsDocument> smsDocumentList = smsESRepository.findByPhoneNumberAndSentTimeBetween(
                getSmsSentBetweenRequest.getPhoneNumber(),
                getSmsSentBetweenRequest.getStartTime().atZone(zoneId).toInstant().toEpochMilli(),
                getSmsSentBetweenRequest.getEndTime().atZone(zoneId).toInstant().toEpochMilli(),
                pageable
        );
        return GetSmsSentBetweenResponse.builder().smsList(smsDocumentList).build();

    }

    public GetSmsContainingTextResponse getSmsContainingText(GetSmsContainingTextRequest getSmsContainingTextRequest) throws Exception{
        Pageable pageable = PageRequest.of(getSmsContainingTextRequest.getPageNumber(), getSmsContainingTextRequest.getSize());
        List<ESSmsDocument> smsDocumentList = smsESRepository.findByMessageContaining(getSmsContainingTextRequest.getText(), pageable);
        return GetSmsContainingTextResponse.builder().data(smsDocumentList).build();
    }

}
