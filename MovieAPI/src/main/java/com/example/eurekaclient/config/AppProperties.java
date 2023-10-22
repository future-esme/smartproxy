package com.example.eurekaclient.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@ConfigurationProperties(prefix = "moviesapi", ignoreUnknownFields = false)
@Validated
public record AppProperties(
        @NotNull String receivingQueue,
        @NotNull String sendingQueue) {
}
