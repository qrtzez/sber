package com.sber.sber.entity.model;

import com.sber.sber.entity.Movie;
import com.sber.sber.entity.Ticket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
public class MovieModel {

    private String title;

    private String genre;

    private List<TicketModel> tickets;

    public static MovieModel toModel(Movie movie) {
        return MovieModel.builder()
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .tickets(movie.getTickets().stream().map(TicketModel::toModel).collect(Collectors.toList()))
                .build();
    }

    public static List<MovieModel> toMovieList(List<Movie> movies) {
        return movies.stream().map(MovieModel::toModel).collect(Collectors.toList());
    }
}
