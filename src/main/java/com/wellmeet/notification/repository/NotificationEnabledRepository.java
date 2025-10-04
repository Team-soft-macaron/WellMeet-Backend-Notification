package com.wellmeet.notification.repository;

import com.wellmeet.notification.consumer.dto.NotificationType;
import com.wellmeet.notification.domain.NotificationEnabled;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEnabledRepository extends JpaRepository<NotificationEnabled, String> {

    List<NotificationEnabled> findByUserIdAndType(String userId, NotificationType type);
}
