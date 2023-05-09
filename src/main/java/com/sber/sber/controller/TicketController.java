package com.sber.sber.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sber.sber.entity.Ticket;
import com.sber.sber.entity.dictionary.NotificationType;
import com.sber.sber.entity.model.HumanModel;
import com.sber.sber.entity.model.TicketModel;
import com.sber.sber.entity.model.dto.TicketDto;
import com.sber.sber.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity createTicket(@RequestBody Ticket ticket,
                                       @RequestParam Long id,
                                       @RequestParam NotificationType notificationType,
                                       @RequestParam  Long id_movie) {
        return ResponseEntity.ok(ticketService.createTicket(ticket, id, notificationType, id_movie));
    }

    @PostMapping("/update")
    public ResponseEntity completeTicket(@RequestParam Long id) {
        return ResponseEntity.ok(ticketService.completeTicket(id));
    }

    @PostMapping("/delete_complete_ticket")
    public ResponseEntity deleteCompleteTicket() {
        ticketService.deleteCompleteTicket();
        return ResponseEntity.ok("Удален список использованных билетов");
    }

    @GetMapping("/find_all_complete")
    public List<TicketModel> findAllCompleteTicket() {
        List<TicketModel> tickets = ticketService.findAllCompleteTicket().stream()
                .map(TicketModel::toModel)
                .collect(Collectors.toList());
        return tickets;
    }

    @PostMapping("/generate_ticket")
    public ResponseEntity generateTicket(@RequestBody TicketDto ticket) throws JsonProcessingException {
        ticketService.generateTicket(ticket.getNumber(),
                ticket.getExecutionDate(),
                ticket.getPrice(),
                ticket.getId());
        String report = String.format("Успешно! Создано: %s билетов\n Двта исполнения: %s\n Цена билетов: %g", ticket.getNumber(),
                ticket.getExecutionDate().toString(), ticket.getPrice());
        return ResponseEntity.ok(report);
    }

    @PostMapping("/buy_ticket")
    public HumanModel buyTicket(@RequestParam Long humanId, @RequestParam Long ticketId) {
        return HumanModel.toModel(ticketService.buyTicket(humanId, ticketId));
    }

    @PostMapping("/gift_ticket")
    public ResponseEntity giftTicket() {
        return ticketService.giftTicket();
    }
}
