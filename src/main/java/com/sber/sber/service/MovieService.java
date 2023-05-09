package com.sber.sber.service;

import com.sber.sber.entity.Movie;
import com.sber.sber.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private NotificationService notificationService;

    public void addMovie(Movie movie) {
        movieRepository.save(movie);
        notificationService.addNewMovieMassNotification(movie);
    }

    public List<Movie> getAllMovie() {
        return movieRepository.findAll();
    }
}
