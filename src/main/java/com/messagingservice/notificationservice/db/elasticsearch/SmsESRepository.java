package com.messagingservice.notificationservice.db.elasticsearch;

import com.messagingservice.notificationservice.models.entity.elasticsearch.ESSmsDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SmsESRepository extends ElasticsearchRepository<ESSmsDocument, Long> {

    List<ESSmsDocument> findByPhoneNumberAndSentTimeBetween(String phoneNumber, long startTime, long endTime, Pageable pageable);

    List<ESSmsDocument> findByMessageContaining(String text, Pageable pageable);

    Optional<ESSmsDocument> findById(long id);
}
