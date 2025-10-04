package com.wellmeet.notification.domain;

import com.wellmeet.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private boolean readStatus;
    private String contents;

    public NotificationHistory(String userId, String contents) {
        this.userId = userId;
        this.readStatus = false;
        this.contents = contents;
    }

    public void markAsRead() {
        this.readStatus = true;
    }
}
