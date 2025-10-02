package com.wellmeet.notification.repository;

import com.wellmeet.notification.domain.OwnerNotificationEnabled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerNotificationEnabledRepository extends JpaRepository<OwnerNotificationEnabled, String> {
}
