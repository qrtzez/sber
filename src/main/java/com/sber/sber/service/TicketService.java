package com.sber.sber.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sber.sber.entity.Human;
import com.sber.sber.entity.Movie;
import com.sber.sber.entity.Notification;
import com.sber.sber.entity.Ticket;
import com.sber.sber.entity.dictionary.NotificationType;
import com.sber.sber.entity.model.HumanModel;
import com.sber.sber.entity.model.TicketModel;
import com.sber.sber.exception.ErrorMessages;
import com.sber.sber.exception.MoneyException;
import com.sber.sber.exception.NotFoundException;
import com.sber.sber.repository.HumanRepository;
import com.sber.sber.repository.MovieRepository;
import com.sber.sber.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
/*import org.springframework.kafka.core.KafkaTemplate;*/
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private HumanRepository humanRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private MovieRepository movieRepository;
 /*   @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;*/

    @Transactional
    public HumanModel createTicket(Ticket ticket, Long id, NotificationType notificationType, Long idMovie) {
        Human human = humanRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.PERSON_NOT_FOUND));
        Movie movie = movieRepository.findById(idMovie).orElseThrow(() -> new NotFoundException(ErrorMessages.MOVIE_NOT_FOUND));
        Notification notification = notificationService.buyTicket(human, ticket, notificationType);
        human.getTickets().add(ticket);
        movie.getTickets().add(ticket);
        human.getNotifications().add(notification);
        humanRepository.save(human);
        movieRepository.save(movie);
        return HumanModel.toModel(human);
    }

    public TicketModel completeTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.PERSON_NOT_FOUND));
        ticket.setComplete(!ticket.isComplete());
        return TicketModel.toModel(ticketRepository.save(ticket));
    }

    @Transactional
    public void deleteCompleteTicket() {
        List<Ticket> tickets = ticketRepository.findAll().stream()
                .filter(Ticket::isComplete)
                .collect(Collectors.toList());

        ticketRepository.deleteAll(tickets);
    }

    public List<Ticket> findAllCompleteTicket() {
        return ticketRepository.findAllCompleteTickets();
    }

    @Transactional
    public void generateTicket(int numberOfTicket, LocalDate executionDate, BigDecimal price, Long id) throws JsonProcessingException {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.MOVIE_NOT_FOUND));
        List<Ticket> generateTickets = new ArrayList<>();
        for (int i = 0; i < numberOfTicket; i++) {
            generateTickets.add(Ticket.builder()
                    .price(price)
                    .executionDate(executionDate)
                    .createDate(LocalDate.now())
                    .complete(false)
                    .isFree(true)
                    .movie(movie)
                    .build());
        }
        movie.getTickets().addAll(generateTickets);
        /*ObjectMapper objectMapper = new ObjectMapper();
        MessageRequest newMessageKafka = MessageRequest.builder()
                .message("Новые билеты добавлены")
                .typeEntity(TypeEntity.TICKET_CREATED)
                .build();
        String jsonKafkaMessage = objectMapper.writeValueAsString(newMessageKafka);*/
        // пока что не работает кафка
        /*kafkaTemplate.send("qrtz", jsonKafkaMessage);*/
        ticketRepository.saveAll(generateTickets);
        movieRepository.save(movie);
    }

    @Transactional
    public Human buyTicket(Long humanId, Long ticketId) {
        Human human = humanRepository.findById(humanId).orElseThrow(() -> new NotFoundException(ErrorMessages.PERSON_NOT_FOUND));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException(ErrorMessages.TICKET_NOT_FOUND));
        if (human.getMoney().compareTo(ticket.getPrice()) < 0) {
            throw new MoneyException(ErrorMessages.INSUFFICIENT_MONEY);
        }
        human.getTickets().add(ticket);
        ticket.setFree(false);
        human.setMoney(human.getMoney().subtract(ticket.getPrice()));
        humanRepository.save(human);
        return human;
    }

    @Transactional
    public ResponseEntity giftTicket() {
        List<Ticket> tickets = ticketRepository.findAllFreeTicket(LocalDate.now());
        Ticket ticket = tickets.get((int) (Math.random() * tickets.size()));
        List<Human> humans = humanRepository.findAll();
        Human human = humans.get((int) (Math.random() * humans.size()));
        human.getTickets().add(ticket);
        ticket.setFree(false);
        humanRepository.save(human);
        return ResponseEntity.ok("Билет с id: " + ticket.getId() +
                " успешно привязан к пользователю с id: " + human.getId());
    }
}
