package com.example.eurekaclient.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record MovieEvent(
        UUID uuid,
        String name,
        List<String> actors,
        BigDecimal bugdet,
        String description,
        String lastUpdatedTime,
        DbOperation dbOperation
){
    public MovieEvent(Movie movie, DbOperation dbOperation) {
        this(movie.getUuid(),movie.getName(), movie.getActors(), movie.getBudget(),
                movie.getDescription(),movie.getLastUpdatedTime().toString(), dbOperation);
    }

    public MovieEvent(UUID uuid, DbOperation dbOperation){
        this(uuid,null,null,null,null,null,dbOperation);
    }
}

