package com.sber.sber.entity.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthHumanDto {

    private String name;
    private String email;
    private String password;
}
