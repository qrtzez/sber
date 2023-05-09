package com.sber.sber.entity.notification;

import com.sber.sber.entity.Human;
import com.sber.sber.entity.Notification;
import com.sber.sber.entity.dictionary.NotificationType;

public interface BaseNotification {

    void send(Notification notification, Human human);

    NotificationType getType();
}
