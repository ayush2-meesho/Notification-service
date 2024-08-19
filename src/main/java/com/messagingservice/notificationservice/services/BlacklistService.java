package com.messagingservice.notificationservice.services;

import com.messagingservice.notificationservice.db.mysql.dao.SmsEntityDao;
import com.messagingservice.notificationservice.models.request.BlacklistRequest;
import com.messagingservice.notificationservice.models.response.BlacklistResponse;
import com.messagingservice.notificationservice.models.response.GetBlacklistResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BlacklistService {

    private final SmsEntityDao smsEntityDao;

    public BlacklistResponse addToBlacklist(BlacklistRequest blacklistRequest) throws Exception{
        List<String> phoneNumberList = blacklistRequest.getPhoneNumbers();
        smsEntityDao.addToBlacklist(phoneNumberList);
        log.info("added phone numbers to blacklist: {}", phoneNumberList);
        return BlacklistResponse.builder().data("Successfully blacklisted").build();
    }

    public BlacklistResponse removeFromBlacklist(BlacklistRequest blacklistRequest) throws Exception{
        List<String> phoneNumberList = blacklistRequest.getPhoneNumbers();
        smsEntityDao.removeFromBlacklist(phoneNumberList);
        log.info("removed phone numbers from blacklist: {}", phoneNumberList);
        return BlacklistResponse.builder().data("Successfully whitelisted").build();
    }

    public GetBlacklistResponse getFromBlacklist() throws Exception{
        List<String> phoneNumberList = smsEntityDao.getAllBlacklisted();
        return GetBlacklistResponse.builder().data(phoneNumberList).build();
    }
}
