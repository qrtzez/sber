package com.sber.sber.service;

import com.sber.sber.entity.Human;
import com.sber.sber.entity.Movie;
import com.sber.sber.entity.Notification;
import com.sber.sber.entity.Ticket;
import com.sber.sber.entity.dictionary.NotificationType;
import com.sber.sber.entity.notification.BaseNotification;
import com.sber.sber.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private HumanRepository humanRepository;

    private final Map<NotificationType, BaseNotification> notificationTypeMap;

    public NotificationService(List<BaseNotification> notifications) {
        this.notificationTypeMap = notifications.stream().collect(Collectors.toMap(BaseNotification::getType, Function.identity()));
    }

    public ResponseEntity manualSend(Notification notification, Long id) {
        Human human = humanRepository.findById(id).orElseThrow();
        send(notification, id);
        return ResponseEntity.ok("Уведомление успешно отправленно челевеку: " + "\n" +
                "Имя: " + human.getName() + "\n"
                + "Тип сообщения: " + notification.getNotificationType());
    }

    public ResponseEntity manualSend(Notification notification, Human human) {
        sendAutoNotification(notification, human);
        return ResponseEntity.ok("Уведомление успешно отправленно челевеку: " + "\n" +
                "Имя: " + human.getName() + "\n"
                + "Тип сообщения: " + notification.getNotificationType());
    }

    public void send(Notification notification, Long id) {
        Human human = humanRepository.findById(id).orElseThrow();
        BaseNotification baseNotification = notificationTypeMap.get(notification.getNotificationType());
        baseNotification.send(notification, human);
    }

    public void sendAutoNotification(Notification notification, Human human) {
        BaseNotification baseNotification = notificationTypeMap.get(notification.getNotificationType());
        baseNotification.send(notification, human);
    }

    public Notification buyTicket(Human human, Ticket ticket, NotificationType notificationType) {
        return Notification.builder()
                .message("Билет успешно куплен! "
                         + "Владелец: " + human.getName()
                         + ". Цена билета: " + ticket.getPrice())
                .notificationType(notificationType)
                .creationDate(LocalDate.now())
                .build();
    }

    @Transactional
    public void addNewMovieMassNotification(Movie movie) {
        Notification notification = Notification.builder()
                .message("Уважаемый пользователь! В прокат вышел фильм \"" + movie.getTitle() + "\"")
                .notificationType(NotificationType.TELEGRAM)
                .creationDate(LocalDate.now())
                .build();
        List<Human> humans = humanRepository.findAll();
        humans.forEach(human -> human.getNotifications().add(notification));
        humanRepository.saveAll(humans);
    }

    public void addNewNewsMassNotification(String title) {
        Notification notification = Notification.builder()
                .message(title)
                .notificationType(NotificationType.TELEGRAM)
                .creationDate(LocalDate.now())
                .build();
        List<Human> humans = humanRepository.findAll();
        humans.forEach(human -> human.getNotifications().add(notification));
        humanRepository.saveAll(humans);
    }
}
