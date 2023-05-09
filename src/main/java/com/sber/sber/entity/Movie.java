package com.sber.sber.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Table(name = "movie")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String genre;

    @OneToMany(cascade = CascadeType.ALL, fetch =  FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private List<Ticket> tickets;
}
