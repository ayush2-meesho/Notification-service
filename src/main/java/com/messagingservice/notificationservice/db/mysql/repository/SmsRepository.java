package com.messagingservice.notificationservice.db.mysql.repository;

import com.messagingservice.notificationservice.models.entity.mysql.SmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmsRepository extends JpaRepository<SmsEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE SmsEntity e SET e.status = :status WHERE e.phoneNumber IN :phoneNumbers AND e.status = :previousStatus")
    int updateStatusByPhoneNumbers(@Param("phoneNumbers") List<String> phoneNumbers, @Param("status") String status, @Param("previousStatus") String previousStatus);

    @Modifying
    @Transactional
    @Query("UPDATE SmsEntity e SET e.status = :status WHERE e.id = :id AND e.status = :previousStatus")
    int updateStatusById(@Param("id") Long id, @Param("status") String status, @Param("previousStatus") String previousStatus);

    @Override
    Optional<SmsEntity> findById(Long id);
}
