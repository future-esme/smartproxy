package com.example.eurekaclient.rest;

import com.example.eurekaclient.domain.Movie;
import com.example.eurekaclient.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
public class MoviesAPI {
    private final Logger log = LoggerFactory.getLogger(MoviesAPI.class);

    private final MovieRepository movieRepository;


    public MoviesAPI(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Movie> getAll(){
        log.info("REST request to get all movies");
        return movieRepository.findAll();
    }

    @PostMapping
    public Optional<Movie> saveMovie(@RequestBody Movie movie){
        log.info("REST request to save movie : {}", movie);
        movie.setLastUpdatedTime(Instant.now());
        return Optional.of(movieRepository.save(movie));
    }

    @Cacheable("movie")
    @GetMapping("/{id}")
    public Optional<Movie> getMovieById(@PathVariable UUID id){
        log.info("REST request to get movie by id : {}", id);
        return movieRepository.findById(id);
    }

    @DeleteMapping
    public void deleteMovies(@RequestParam UUID id) {
        log.info("REST request to movie by id {} ",id);
        movieRepository.deleteById(id);
    }
}

