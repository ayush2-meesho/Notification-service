package com.messagingservice.notificationservice.services;

import com.messagingservice.notificationservice.db.mysql.dao.SmsEntityDao;
import com.messagingservice.notificationservice.models.request.BlacklistRequest;
import com.messagingservice.notificationservice.models.response.BlacklistResponse;
import com.messagingservice.notificationservice.models.response.GetBlacklistResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlacklistServiceTest {

    @Mock
    private SmsEntityDao smsEntityDao;

    @InjectMocks
    private BlacklistService blacklistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testRemoveFromBlacklist() throws Exception {
        List<String> phoneNumbers = Arrays.asList("+123456789", "+987654321");
        BlacklistRequest blacklistRequest = new BlacklistRequest();
        blacklistRequest.setPhoneNumbers(phoneNumbers);

        when(smsEntityDao.removeFromBlacklist(any())).thenReturn(any(Integer.class));

        BlacklistResponse response = blacklistService.removeFromBlacklist(blacklistRequest);

        assertNotNull(response);
        assertEquals("Successfully whitelisted", response.getData());
        verify(smsEntityDao, times(1)).removeFromBlacklist(phoneNumbers);
    }

    @Test
    void testGetFromBlacklist() throws Exception {
        List<String> phoneNumbers = Arrays.asList("+123456789", "+987654321");

        when(smsEntityDao.getAllBlacklisted()).thenReturn(phoneNumbers);

        GetBlacklistResponse response = blacklistService.getFromBlacklist();

        assertNotNull(response);
        assertEquals(phoneNumbers, response.getData());
        verify(smsEntityDao, times(1)).getAllBlacklisted();
    }
}
