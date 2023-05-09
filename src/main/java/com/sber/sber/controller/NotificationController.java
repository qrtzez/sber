package com.sber.sber.controller;

import com.sber.sber.entity.Human;
import com.sber.sber.entity.Notification;
import com.sber.sber.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity sendNotification(@RequestBody Notification notification,
                                           @RequestParam Long id) {
        return notificationService.manualSend(notification, id);
    }
}
