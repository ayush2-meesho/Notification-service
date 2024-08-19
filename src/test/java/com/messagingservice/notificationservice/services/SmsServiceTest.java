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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SmsServiceTest {

    @Mock
    private SmsRepository smsRepository;

    @Mock
    private SmsESRepository smsESRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private SmsService smsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSmsRequest() throws Exception {
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        SmsEntity smsEntity = new SmsEntity();
        smsEntity.setId(1L);

        when(smsRepository.save(any(SmsEntity.class))).thenReturn(smsEntity);

        SendSmsResponse response = smsService.saveSmsRequest(sendSmsRequest);

        assertNotNull(response);
        verify(kafkaProducerService).sendMessage("notification", smsEntity.getId());
    }

    @Test
    public void testGetRequestDetails_Found() throws Exception {
        SmsEntity smsEntity = new SmsEntity();
        smsEntity.setId(1L);

        when(smsRepository.findById(anyLong())).thenReturn(Optional.of(smsEntity));

        GetSmsDetailsResponse response = smsService.getRequestDetails(1L);

        assertNotNull(response);
        assertEquals(smsEntity, response.getSmsEntity());
    }

    @Test
    public void testGetRequestDetails_NotFound() throws Exception {
        when(smsRepository.findById(anyLong())).thenReturn(Optional.empty());

        GetSmsDetailsResponse response = smsService.getRequestDetails(1L);

        assertNotNull(response);
        assertEquals(null, response.getSmsEntity());
    }

    @Test
    public void testSaveSmsToES() throws Exception {
        SmsEntity smsEntity = new SmsEntity();
        ESSmsDocument smsDocument = new ESSmsDocument();
        when(smsESRepository.save(any())).thenReturn(smsDocument);

        ESSmsDocument result = smsService.saveSmsToES(smsEntity);

        verify(smsESRepository).save(any());
    }

    @Test
    public void testGetSentBetweenDetails() throws Exception {
        GetSmsSentBetweenRequest request = new GetSmsSentBetweenRequest();
        request.setPhoneNumber("1234567890");
        request.setStartTime(LocalDateTime.now().minusDays(1));
        request.setEndTime(LocalDateTime.now());
        request.setPageNumber(0);
        request.setSize(10);

        when(smsESRepository.findByPhoneNumberAndSentTimeBetween(anyString(), anyLong(), anyLong(), any(Pageable.class)))
                .thenReturn(Collections.emptyList());

        GetSmsSentBetweenResponse response = smsService.getSentBetweenDetails(request);

        assertNotNull(response);
        assertEquals(0, response.getSmsList().size());
    }

    @Test
    public void testGetSmsContainingText() throws Exception {
        GetSmsContainingTextRequest request = new GetSmsContainingTextRequest();
        request.setText("sample");
        request.setPageNumber(0);
        request.setSize(10);

        when(smsESRepository.findByMessageContaining(anyString(), any(Pageable.class)))
                .thenReturn(Collections.emptyList());

        GetSmsContainingTextResponse response = smsService.getSmsContainingText(request);

        assertNotNull(response);
        assertEquals(0, response.getData().size());
    }
}
