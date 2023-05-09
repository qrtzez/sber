package com.sber.sber.entity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sber.sber.entity.Ticket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class TicketModel {

    private BigDecimal price;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate createDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate executionDate;

    private boolean complete;

    public static TicketModel toModel(Ticket ticket) {
        return TicketModel.builder()
                .price(ticket.getPrice())
                .createDate(ticket.getCreateDate())
                .executionDate(ticket.getExecutionDate())
                .complete(ticket.isComplete())
                .build();
    }
}
