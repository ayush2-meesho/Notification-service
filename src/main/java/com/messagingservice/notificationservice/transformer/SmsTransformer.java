package com.messagingservice.notificationservice.transformer;



import com.messagingservice.notificationservice.models.entity.elasticsearch.ESSmsDocument;
import com.messagingservice.notificationservice.models.entity.mysql.SmsEntity;
import com.messagingservice.notificationservice.models.request.SendSmsRequest;
import com.messagingservice.notificationservice.models.response.SendSmsResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

public class SmsTransformer {

    public static SmsEntity getSmsEntity(SendSmsRequest sendSmsRequest) {
        return SmsEntity.builder()
                .failureCode(0)
                .failureComments(null)
                .message(sendSmsRequest.getMessage())
                .status("pending")
                .phoneNumber(sendSmsRequest.getPhoneNumber())
                .build();
    }

    public static SendSmsResponse getSendSmsResponse(SmsEntity smsEntity) {
        return SendSmsResponse
                .builder()
                .data(SendSmsResponse.Data.builder().requestId(smsEntity.getId()).comments("Succesfully Sent").build())
                .build();
    }

    public static ESSmsDocument getESSmsDocument(SmsEntity smsEntity) {

        if(Objects.isNull(smsEntity.getUpdatedAt())) {
            return null;
        }

        ZoneId zoneId = ZoneId.systemDefault();

        // Convert LocalDateTime to Instant (UTC)
        Instant instant = smsEntity.getUpdatedAt().atZone(zoneId).toInstant();

        // Get the epoch milliseconds
        long epochMillis = instant.toEpochMilli();

        return ESSmsDocument.builder()
                .id(smsEntity.getId())
                .phoneNumber(smsEntity.getPhoneNumber())
                .message(smsEntity.getMessage())
                .sentTime(epochMillis)
                .build();
    }

}
