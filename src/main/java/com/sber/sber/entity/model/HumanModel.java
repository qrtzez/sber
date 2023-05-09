package com.sber.sber.entity.model;

import com.sber.sber.entity.Human;
import com.sber.sber.entity.News;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
public class HumanModel {

    private Long id;

    private String name;

    private BigDecimal money;

    private List<TicketModel> tickets;

    private List<NotificationModel> notifications;

    private List<NewsModel> news;

    public static HumanModel toModel(Human human) {
        return HumanModel.builder()
                .id(human.getId())
                .name(human.getName())
                .money(human.getMoney())
                .tickets(human.getTickets().stream().map(TicketModel::toModel).collect(Collectors.toList()))
                .notifications(human.getNotifications().stream().map(NotificationModel::toModel).collect(Collectors.toList()))
                .build();
    }

    public static List<HumanModel> toModelList(List<Human> humans) {
        return humans.stream().map(HumanModel::toModel).collect(Collectors.toList());
    }
}
