package com.sber.sber.event;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EventService {

    @EventListener(condition = "#humanObject.human.money < 100")
    public void updateHumanBeforeSave(EventHumanObject humanObject) {
            humanObject.getHuman().setMoney(humanObject.getHuman().getMoney().add(BigDecimal.valueOf(100L)));
    }

}
