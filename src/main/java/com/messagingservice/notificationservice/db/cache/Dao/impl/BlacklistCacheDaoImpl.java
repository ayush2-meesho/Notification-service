package com.messagingservice.notificationservice.db.cache.Dao.impl;


import com.messagingservice.notificationservice.db.cache.Dao.BlacklistCacheDao;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BlacklistCacheDaoImpl implements BlacklistCacheDao {

    private static final String REDIS_KEY_PREFIX = "blacklist";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void multiSet(List<String> phoneNumberList) {
        Map<String, String> keyValueMap = phoneNumberList.stream().collect(Collectors.toMap(this::getRedisKey, item -> item));
        redisTemplate.opsForValue().multiSet(keyValueMap);
        log.info("Successfully added numbers in blacklist: {}", phoneNumberList);
    }

    @Override
    public void remove(List<String> phoneNumberList) {
        List<String> redisKeyList = phoneNumberList.stream().map(this::getRedisKey).collect(Collectors.toList());
        redisTemplate.delete(redisKeyList);
        log.info("Successfully removed phoneNumbers from blacklist: {}", phoneNumberList);
    }

    @Override
    public List<String> multiGet() {
        List<String> blacklistedNumbers = redisTemplate.keys("*").stream().map(key -> (String) redisTemplate.opsForValue().get(key)).collect(Collectors.toList());
        log.info("Successfully retrieved phoneNumbers from blacklist: {}", blacklistedNumbers);
        return blacklistedNumbers;
    }

    private String getRedisKey(String phoneNumber) {
        return String.format(REDIS_KEY_PREFIX + ":%s", phoneNumber);
    }


}
