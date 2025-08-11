package com.wellmeet.webpush.repository;

import com.wellmeet.webpush.domain.PushSubscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PushSubscriptionRepository extends JpaRepository<PushSubscription, Long> {

    List<PushSubscription> findByUserId(Long userId);

    void deleteByUserIdAndEndpoint(Long userId, String endpoint);

    boolean existsByUserIdAndEndpoint(Long userId, String endpoint);
}
