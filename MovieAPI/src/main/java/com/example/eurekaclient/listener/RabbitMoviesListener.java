package com.example.eurekaclient.listener;
import com.example.eurekaclient.domain.DbOperation;
import com.example.eurekaclient.domain.Movie;
import com.example.eurekaclient.domain.MovieEvent;
import com.example.eurekaclient.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMoviesListener {
    private final Logger log = LoggerFactory.getLogger(RabbitMoviesListener.class);
    private final MovieRepository movieRepository;

    public RabbitMoviesListener(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @RabbitListener(queues = "${moviesapi.receiving-queue}")
    public void receiveMessage(MovieEvent movieEvent) {
        log.info("Recive  : {}", movieEvent);
        if (movieEvent.dbOperation().equals(DbOperation.SAVE)){
            var toSave = new Movie(movieEvent);
            if (!movieRepository.existsByUuidAndLastUpdatedTimeIs(toSave.getUuid(), toSave.getLastUpdatedTime()))
                movieRepository.save(toSave);
        }
        if (movieEvent.dbOperation().equals(DbOperation.DELETE)){
            if (movieRepository.existsByUuid(movieEvent.uuid()))
                movieRepository.deleteById(movieEvent.uuid());
        }
    }
}
