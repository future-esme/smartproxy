package com.example.eurekaclient.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Document
public class Movie extends MongoDocument {

    public String name;
    public List<String> actors;

    public BigDecimal budget;

    public String description;

    public Movie() {
    }

    public Movie(MovieEvent movieEvent) {
        this.uuid = movieEvent.uuid();
        this.name = movieEvent.name();
        this.actors = movieEvent.actors();
        this.budget = movieEvent.bugdet();
        this.description = movieEvent.description();
        this.lastUpdatedTime = ZonedDateTime.parse(movieEvent.lastUpdatedTime(),
                DateTimeFormatter.ofPattern(("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSX"))).toInstant();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", actors=" + actors +
                ", budget=" + budget +
                ", description='" + description + '\'' +
                '}';
    }


    
}
