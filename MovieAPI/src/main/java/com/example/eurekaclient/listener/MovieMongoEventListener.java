package com.example.eurekaclient.listener;

import com.example.eurekaclient.config.AppProperties;
import com.example.eurekaclient.domain.DbOperation;
import com.example.eurekaclient.domain.Movie;
import com.example.eurekaclient.domain.MovieEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MovieMongoEventListener extends AbstractMongoEventListener<Movie> {

    private final Logger log = LoggerFactory.getLogger(MovieMongoEventListener.class);
    private final RabbitTemplate rabbitTemplate;

    private final AppProperties appProperties;

    public MovieMongoEventListener(RabbitTemplate rabbitTemplate,
                                   AppProperties appProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.appProperties = appProperties;
    }


    @Override
    public void onAfterSave(AfterSaveEvent<Movie> event) {
        Movie savedMovie = event.getSource();
        log.info("savedMovie : {}", savedMovie);
        rabbitTemplate.convertAndSend(appProperties.sendingQueue() + "Exchange",
                appProperties.sendingQueue() + "RoutingKey", new MovieEvent(savedMovie, DbOperation.SAVE));
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Movie> event) {
        UUID uuid = UUID.fromString(event.getSource().get("_id").toString());
        log.info("deleted movie : {}", uuid);
        rabbitTemplate.convertAndSend(appProperties.sendingQueue() + "Exchange",
                appProperties.sendingQueue() + "RoutingKey",
                new MovieEvent(uuid,DbOperation.DELETE));
    }

}
