package com.messagingservice.notificationservice.db.cache.Dao;

import java.util.List;

public interface BlacklistCacheDao {

    void multiSet(List<String> phoneNumberList);

    void remove(List<String> phoneNumberList);

    List<String> multiGet();
}
