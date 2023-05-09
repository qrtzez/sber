package com.sber.sber.entity.model;

import com.sber.sber.entity.Notification;
import com.sber.sber.entity.dictionary.NotificationType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class NotificationModel {

    private String message;

    private NotificationType notificationType;

    private LocalDate creationDate;

    public static NotificationModel toModel(Notification notification) {
        return NotificationModel.builder()
                .message(notification.getMessage())
                .notificationType(notification.getNotificationType())
                .creationDate(notification.getCreationDate())
                .build();
    }

}
