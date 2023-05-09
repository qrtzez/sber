package com.sber.sber.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {
    PERSON_NOT_FOUND("Неудачный поиск сущности/сущностей"),
    INSUFFICIENT_MONEY("Недостаточно средств"),
    TICKET_NOT_FOUND("Неудачный поиск билета"),
    MOVIE_NOT_FOUND("Неудачный поиск фильма"),
    MISSING_PASSWORD("При регистрации не указан пароль"),
    REPEATED_EMAIL("Пользователь с таким Email уже существует"),
    MISSING_EMAIL("При регистрации не указан Email"),
    MISSING_NAME("При регистрации не указано имя"),
    JWT_EXPIRED("JWT просрочен");


    private String description;

}
