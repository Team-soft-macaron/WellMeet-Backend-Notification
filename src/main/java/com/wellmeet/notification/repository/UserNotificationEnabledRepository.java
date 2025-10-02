package com.wellmeet.notification.repository;

import com.wellmeet.notification.domain.UserNotificationEnabled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationEnabledRepository extends JpaRepository<UserNotificationEnabled, String> {
}
