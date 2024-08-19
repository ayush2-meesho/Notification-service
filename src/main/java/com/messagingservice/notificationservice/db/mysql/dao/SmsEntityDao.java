package com.messagingservice.notificationservice.db.mysql.dao;

import com.messagingservice.notificationservice.db.cache.Dao.BlacklistCacheDao;
import com.messagingservice.notificationservice.db.mysql.repository.SmsRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SmsEntityDao {
    private final Logger logger = LoggerFactory.getLogger(SmsEntityDao.class);

    private final SmsRepository smsRepository;

    private final BlacklistCacheDao blacklistCacheDao;

    public int addToBlacklist(List<String> phoneNumberList) {
        blacklistCacheDao.multiSet(phoneNumberList);
        int updatedEntities = smsRepository.updateStatusByPhoneNumbers(phoneNumberList, "Blacklisted", "pending");
        logger.info("Successfully Blacklisted in sql: {}", phoneNumberList);
        return updatedEntities;
    }

    public int removeFromBlacklist(List<String> phoneNumberList) {
        blacklistCacheDao.remove(phoneNumberList);
        int updatedEntities = smsRepository.updateStatusByPhoneNumbers(phoneNumberList, "pending", "Blacklisted");
        logger.info("Successfully removed phone numbers from blacklist in sql: {}", phoneNumberList);
        return updatedEntities;
    }

    public List<String> getAllBlacklisted() {
        return blacklistCacheDao.multiGet();
    }
}
