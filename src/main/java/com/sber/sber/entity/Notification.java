package com.sber.sber.entity;

import com.sber.sber.entity.dictionary.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
