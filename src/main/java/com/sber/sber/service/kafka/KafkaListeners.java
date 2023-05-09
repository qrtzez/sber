package com.sber.sber.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sber.sber.entity.Human;
import com.sber.sber.entity.Notification;
import com.sber.sber.entity.dictionary.NotificationType;
import com.sber.sber.entity.model.dto.MessageResponse;
import com.sber.sber.event.TypeEntity;
import com.sber.sber.repository.HumanRepository;
import com.sber.sber.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class KafkaListeners {

    @Autowired
    private HumanRepository humanRepository;
    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "qrtz", groupId = "groupId")
    public void listen(String data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageResponse messageResponse = objectMapper.readValue(data, MessageResponse.class);
        if (messageResponse.getTypeEntity().equals(TypeEntity.TICKET_CREATED)) {
            notificationAllHumanAboutCreatedTicket();
        }
    }

    public void notificationAllHumanAboutCreatedTicket() {
        List<Human> allHuman = humanRepository.findAll();
        Notification notification = Notification.builder()
                .message("Уважаемый клиет! Уведомляем Вас о том, что были созданы новые билеты!")
                .notificationType(NotificationType.SMS)
                .creationDate(LocalDate.now())
                .build();
        allHuman.stream().forEach(human -> notificationService.sendAutoNotification(notification, human));
    }
}
