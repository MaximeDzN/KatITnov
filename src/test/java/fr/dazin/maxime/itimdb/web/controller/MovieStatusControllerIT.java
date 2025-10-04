package fr.dazin.maxime.itimdb.web.controller;

import fr.dazin.maxime.itimdb.domain.Movie;
import fr.dazin.maxime.itimdb.domain.User;
import fr.dazin.maxime.itimdb.repository.*;
import fr.dazin.maxime.itimdb.repository.MovieRepository;
import fr.dazin.maxime.itimdb.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MovieStatusControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieStatusRepository movieStatusRepository;

    private User user;
    private Movie movie;
    private Movie movie2;


    @BeforeEach
    void setUp() {
        movieStatusRepository.deleteAll();
        userRepository.deleteAll();
        movieRepository.deleteAll();
        user = userRepository.save(User.builder().userName("Alice").build());
        movie = movieRepository.save(
                Movie.builder()
                        .title("Inception")
                        .description("Rêves imbriqués")
                        .rating(9.0)
                        .releaseDate(LocalDate.of(2010, 7, 21))
                        .build()
        );

        movie2 = movieRepository.save(
                Movie.builder()
                        .title("Un film 2")
                        .description("2")
                        .rating(7.0)
                        .releaseDate(LocalDate.of(2012, 7, 21))
                        .build()
        );
    }

    @Test
    void whenUpdateFavorite_thenStatusNoContent() throws Exception {
        mockMvc.perform(put("/users/{userId}/movies/{movieId}/favorite", user.getId(), movie.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("true")).andExpect(status().isNoContent());
    }

    @Test
    void whenUpdateSeen_thenStatusNoContent() throws Exception {
        mockMvc.perform(put("/users/{userId}/movies/{movieId}/seen", user.getId(), movie.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("true")).andExpect(status().isNoContent());
    }

    @Test
    void whenListFavoritesOrderByRatingAsc_thenReturnJsonArray() throws Exception {
        mockMvc.perform(put("/users/{userId}/movies/{movieId}/favorite", user.getId(), movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/users/{userId}/movies/{movieId}/favorite", user.getId(), movie2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/{userId}/movies/favorites", user.getId()).param("sortBy", "rating").param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Un film 2")))
                .andExpect(jsonPath("$[0].favorite", is(true)))
                .andExpect(jsonPath("$[1].title", is("Inception")))
                .andExpect(jsonPath("$[1].favorite", is(true)));
    }

    @Test
    void whenListFavoritesOrderByRatingDesc_thenReturnJsonArray() throws Exception {
        mockMvc.perform(put("/users/{userId}/movies/{movieId}/favorite", user.getId(), movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/users/{userId}/movies/{movieId}/favorite", user.getId(), movie2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/{userId}/movies/favorites", user.getId()).param("sortBy", "rating").param("order", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Inception")))
                .andExpect(jsonPath("$[0].favorite", is(true)))
                .andExpect(jsonPath("$[1].title", is("Un film 2")))
                .andExpect(jsonPath("$[1].favorite", is(true)));
    }

    @Test
    void whenListFavoritesOrderByReleaseDateAsc_thenReturnJsonArray() throws Exception {
        mockMvc.perform(put("/users/{userId}/movies/{movieId}/favorite", user.getId(), movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/users/{userId}/movies/{movieId}/favorite", user.getId(), movie2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/{userId}/movies/favorites", user.getId()).param("sortBy", "releaseDate").param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Inception")))
                .andExpect(jsonPath("$[0].favorite", is(true)))
                .andExpect(jsonPath("$[1].title", is("Un film 2")))
                .andExpect(jsonPath("$[1].favorite", is(true)));
    }

    @Test
    void whenListFavoritesOrderByReleaseDateDesc_thenReturnJsonArray() throws Exception {
        mockMvc.perform(put("/users/{userId}/movies/{movieId}/favorite", user.getId(), movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/users/{userId}/movies/{movieId}/favorite", user.getId(), movie2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/{userId}/movies/favorites", user.getId()).param("sortBy", "releaseDate").param("order", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Un film 2")))
                .andExpect(jsonPath("$[0].favorite", is(true)))
                .andExpect(jsonPath("$[1].title", is("Inception")))
                .andExpect(jsonPath("$[1].favorite", is(true)));
    }


    @Test
    void whenListFavoritesOrderByWrongTypeAndEmptyOrder_thenReturnJsonArray() throws Exception {
        mockMvc.perform(put("/users/{userId}/movies/{movieId}/favorite", user.getId(), movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/users/{userId}/movies/{movieId}/favorite", user.getId(), movie2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/{userId}/movies/favorites", user.getId()).param("sortBy", "dunno").param("order", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Inception")))
                .andExpect(jsonPath("$[0].favorite", is(true)))
                .andExpect(jsonPath("$[1].title", is("Un film 2")))
                .andExpect(jsonPath("$[1].favorite", is(true)));
    }

    @Test
    void whenListSeenMovies_thenReturnJsonArray() throws Exception {
        mockMvc.perform(put("/users/{userId}/movies/{movieId}/seen", user.getId(), movie.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/{userId}/movies/seen", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Inception")))
                .andExpect(jsonPath("$[0].seen", is(true)));
    }

    @Test
    void whenListUnseenMovies_thenReturnJsonArray() throws Exception {
        mockMvc.perform(get("/users/{userId}/movies/unseen", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Inception")))
                .andExpect(jsonPath("$[0].seen", is(false)))
                .andExpect(jsonPath("$[0].favorite", is(false)));
    }

    @Test
    void whenUpdateFavoriteUserNotFound_thenReturn404() throws Exception {
        mockMvc.perform(put("/users/999/movies/1/favorite").contentType(MediaType.APPLICATION_JSON).content("true"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id 999"));
    }

    @Test
    void whenUpdateFavoriteMovieNotFound_thenReturn404() throws Exception {
        mockMvc.perform(put("/users/{userId}/movies/999/favorite",user.getId()).contentType(MediaType.APPLICATION_JSON).content("true"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Movie not found with id 999"));
    }


}