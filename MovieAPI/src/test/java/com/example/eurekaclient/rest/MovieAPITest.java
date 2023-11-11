package com.example.eurekaclient.rest;

import com.example.eurekaclient.domain.Movie;
import com.example.eurekaclient.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@IntegrationTest
@AutoConfigureMockMvc
class MovieAPITest {

    @MockBean
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    @Test
    void testGetAllMovies() {
        Movie[] moviesExpected = new Movie[] { getMockMovie() };
        Movie[] moviesResult = new Movie[] { getMockMovie() };
        assertArrayEquals(moviesExpected, moviesResult);
    }

    private Movie getMockMovie() {
        var movie = new Movie();
        movie.name = "ABC test";
        movie.budget = BigDecimal.valueOf(100000000000L);
        movie.description = "Description test";
        movie.actors = List.of("Actor 1", "Actor 2");
        return movie;
    }
}
