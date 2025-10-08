package com.wellmeet.notification.email.repository;

import com.wellmeet.notification.email.domain.EmailSubscription;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSubscriptionRepository extends JpaRepository<EmailSubscription, Long> {

    Optional<EmailSubscription> findByUserId(String userId);
}
