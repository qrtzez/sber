package com.sber.sber.entity.notification;

import com.sber.sber.entity.Human;
import com.sber.sber.entity.Notification;
import com.sber.sber.entity.dictionary.NotificationType;
import com.sber.sber.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsBaseNotification implements BaseNotification {

    @Autowired
    private HumanRepository humanRepository;

    @Override
    public void send(Notification notification, Human human) {
        //логика отправки уведомления по смс
        human.getNotifications().add(notification);
        humanRepository.save(human);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }
}
