package com.sber.sber.controller;

import com.sber.sber.entity.Movie;
import com.sber.sber.entity.model.MovieModel;
import com.sber.sber.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity addMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
        return ResponseEntity.ok("Фильм " + movie.getTitle() + " успешно добавлен");
    }

    @GetMapping("/all")
    public ResponseEntity allMovie() {
        return ResponseEntity.ok(MovieModel.toMovieList(movieService.getAllMovie()));
    }
}
