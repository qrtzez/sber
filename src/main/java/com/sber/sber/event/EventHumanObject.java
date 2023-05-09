package com.sber.sber.event;

import com.sber.sber.entity.Human;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public class EventHumanObject {

    private Human human;

    private boolean flag;

}
