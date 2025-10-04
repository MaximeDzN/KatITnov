package fr.dazin.maxime.itimdb.repository;

import fr.dazin.maxime.itimdb.domain.Movie;
import fr.dazin.maxime.itimdb.domain.MovieStatus;
import fr.dazin.maxime.itimdb.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieStatusRepository movieStatusRepository;

    private User user;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder().userName("testuser").build());

        movie1 = movieRepository.save(Movie.builder()
                .title("Film 1")
                .releaseDate(LocalDate.of(2020,1,1))
                .build());

        movie2 = movieRepository.save(Movie.builder()
                .title("Film 2")
                .releaseDate(LocalDate.of(2021,1,1))
                .build());

        movie3 = movieRepository.save(Movie.builder()
                .title("Film 3")
                .releaseDate(LocalDate.of(2022,1,1))
                .build());

    }

    @Test
    void whenUserHasSeenAllMovies_thenUnseenIsEmpty() {
        movieStatusRepository.save(MovieStatus.builder().user(user).movie(movie1).seen(true).build());
        movieStatusRepository.save(MovieStatus.builder().user(user).movie(movie2).seen(true).build());
        movieStatusRepository.save(MovieStatus.builder().user(user).movie(movie3).seen(true).build());

        List<Movie> unseen = movieRepository.findUnseenByUser(user);

        assertThat(unseen).isEmpty();
    }

    @Test
    void whenUserHasSeenNoMovies_thenAllMoviesAreUnseen(){
        List<Movie> unseen = movieRepository.findAll();
        assertThat(unseen)
                .hasSize(3)
                .extracting(Movie::getTitle)
                .containsExactlyInAnyOrder("Film 1", "Film 2", "Film 3");
    }

    @Test
    void whenUserSeenOneMovie_thenItIsExcludedFromUnseen() {
        movieStatusRepository.save(MovieStatus.builder()
                .user(user)
                .movie(movie1)
                .seen(true)
                .favorite(false)
                .build());

        List<Movie> unseenMovies = movieRepository.findUnseenByUser(user);

        assertThat(unseenMovies).hasSize(2)
                .extracting(Movie::getTitle)
                .containsExactlyInAnyOrder("Film 2", "Film 3")
                .doesNotContain("Film 1");
    }

    @Test
    void whenUserHasMovieStatusButNotSeen_thenMovieIsStillUnseen(){
        movieStatusRepository.save(MovieStatus.builder()
                .user(user)
                .movie(movie1)
                .seen(false)
                .favorite(false)
                .build());

        List<Movie> unseenMovies = movieRepository.findUnseenByUser(user);

        assertThat(unseenMovies).hasSize(3).extracting(Movie::getTitle)
                .containsExactlyInAnyOrder("Film 1", "Film 2", "Film 3");
    }

    @Test
    void whenNoMovieExists_thenUnseenIsEmpty(){
        movieRepository.deleteAll();
        List<Movie> unseenMovies = movieRepository.findAll();
        assertThat(unseenMovies).isEmpty();
    }

}