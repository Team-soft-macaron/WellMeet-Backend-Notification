package com.wellmeet.notification.repository;

import com.wellmeet.notification.domain.NotificationEnabled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEnabledRepository extends JpaRepository<NotificationEnabled, String> {
}
