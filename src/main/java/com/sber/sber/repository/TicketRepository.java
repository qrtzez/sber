package com.sber.sber.repository;

import com.sber.sber.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("select t from Ticket t where t.complete = true ")
    List<Ticket> findAllCompleteTickets();

    List<Ticket> findByExecutionDateLessThan(LocalDate nowLocalDate);

    @Query("select t from Ticket t where t.complete = false and t.executionDate > ?1 and t.isFree = true ")
    List<Ticket> findAllFreeTicket(LocalDate date);
}
