package com.sber.sber.entity.model.dto;

import com.sber.sber.event.TypeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MessageResponse {

    private String message;

    private TypeEntity typeEntity;
}
