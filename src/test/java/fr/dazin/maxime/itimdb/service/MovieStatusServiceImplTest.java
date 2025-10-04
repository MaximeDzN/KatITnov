package fr.dazin.maxime.itimdb.service;

import fr.dazin.maxime.itimdb.domain.Movie;
import fr.dazin.maxime.itimdb.domain.MovieStatus;
import fr.dazin.maxime.itimdb.domain.User;
import fr.dazin.maxime.itimdb.exception.MovieNotFoundException;
import fr.dazin.maxime.itimdb.exception.UserNotFoundException;
import fr.dazin.maxime.itimdb.repository.MovieRepository;
import fr.dazin.maxime.itimdb.repository.MovieStatusRepository;
import fr.dazin.maxime.itimdb.service.util.SortFactory;
import fr.dazin.maxime.itimdb.web.dto.MovieWithStatusDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieStatusServiceImplTest {

    @Mock
    MovieRepository movieRepository;

    @Mock
    MovieStatusRepository movieStatusRepository;

    @Mock
    UserService userService;

    @Mock
    MovieService movieService;

    @Mock
    SortFactory sortFactory;

    @InjectMocks
    MovieStatusServiceImpl movieStatusServiceImpl;

    private User user;
    private Movie movie;
    private Movie movie3;
    private MovieStatus status;
    private MovieStatus status2;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).userName("Alice").build();
        movie = Movie.builder().id(10L).title("Film A").rating(2.0).releaseDate(LocalDate.of(2020, 1, 1)).build();
        Movie movie2 = Movie.builder().id(11L).title("Film B").rating(10.0).releaseDate(LocalDate.of(2019, 1, 1)).build();
        movie3 = Movie.builder().id(12L).title("Film C").rating(7.0).releaseDate(LocalDate.of(2017, 1, 1)).build();
        status = MovieStatus.builder().id(1L).user(user).movie(movie).favorite(true).seen(true).build();
        status2 = MovieStatus.builder().id(2L).user(user).movie(movie2).favorite(true).seen(true).build();
    }

    @Test
    void whenNoExistingStatus_thenCreateNewWithFavoriteTrue() {
        when(userService.findById(1L)).thenReturn(user);
        when(movieService.findById(10L)).thenReturn(movie);
        when(movieStatusRepository.findByUserAndMovie(user, movie)).thenReturn(Optional.empty());

        movieStatusServiceImpl.updateFavorite(1L, 10L, true);

        verify(userService).findById(1L);
        verify(movieService).findById(10L);
        verify(movieStatusRepository).save(argThat(movieStatus ->
                movieStatus.getUser().equals(user) &&
                        movieStatus.getMovie().equals(movie) &&
                        movieStatus.isSeen() &&
                        movieStatus.isFavorite()
        ));
    }

    @Test
    void whenExistingStatus_thenUpdateFavoriteAndSetSeenTrue() {
        MovieStatus existing = MovieStatus.builder()
                        .user(user).movie(movie).seen(false).favorite(false).build();

        when(userService.findById(1L)).thenReturn(user);
        when(movieService.findById(10L)).thenReturn(movie);
        when(movieStatusRepository.findByUserAndMovie(user, movie)).thenReturn(Optional.of(existing));

        movieStatusServiceImpl.updateFavorite(1L, 10L, true);

        assertThat(existing.isSeen()).isTrue();
        assertThat(existing.isFavorite()).isTrue();

        verify(userService).findById(1L);
        verify(movieService).findById(10L);
        verify(movieStatusRepository).save(existing);
    }

    @Test
    void whenExistingStatusAndUnsetFavorite_thenFavoriteFalseButSeenTrue(){
        MovieStatus existing = MovieStatus.builder()
                .user(user).movie(movie).seen(true).favorite(true).build();

        when(userService.findById(1L)).thenReturn(user);
        when(movieService.findById(10L)).thenReturn(movie);
        when(movieStatusRepository.findByUserAndMovie(user, movie)).thenReturn(Optional.of(existing));

        movieStatusServiceImpl.updateFavorite(1L, 10L, false);

        assertThat(existing.isSeen()).isTrue();
        assertThat(existing.isFavorite()).isFalse();

        verify(userService).findById(1L);
        verify(movieService).findById(10L);
        verify(movieStatusRepository).save(existing);

    }

    @Test
    void whenNoExistingStatus_thenCreateNewWithSeenTrue() {
        when(userService.findById(1L)).thenReturn(user);
        when(movieService.findById(10L)).thenReturn(movie);
        when(movieStatusRepository.findByUserAndMovie(user, movie)).thenReturn(Optional.empty());

        movieStatusServiceImpl.updateSeen(1L, 10L);

        verify(userService).findById(1L);
        verify(movieService).findById(10L);
        verify(movieStatusRepository).save(argThat(movieStatus ->
                movieStatus.getUser().equals(user) &&
                        movieStatus.getMovie().equals(movie) &&
                        movieStatus.isSeen() &&
                        !movieStatus.isFavorite()
        ));
    }

    @Test
    void whenExistingStatus_thenUpdateSeenTrue() {
        MovieStatus existing = MovieStatus.builder()
                .user(user).movie(movie).seen(false).favorite(false).build();

        when(userService.findById(1L)).thenReturn(user);
        when(movieService.findById(10L)).thenReturn(movie);
        when(movieStatusRepository.findByUserAndMovie(user, movie)).thenReturn(Optional.of(existing));

        movieStatusServiceImpl.updateSeen(1L, 10L);

        assertThat(existing.isSeen()).isTrue();
        assertThat(existing.isFavorite()).isFalse();

        verify(userService).findById(1L);
        verify(movieService).findById(10L);
        verify(movieStatusRepository).save(existing);
    }


    @Test
    void whenFavoritesExistSortedByReleaseDate_thenReturnFavoritesOrderedByReleaseDate() {
        Sort sort = Sort.by(Sort.Direction.ASC, "movie.releaseDate");

        when(userService.findById(1L)).thenReturn(user);
        when(sortFactory.buildSort("releaseDate", "asc")).thenReturn(sort);
        when(movieStatusRepository.findByUserAndFavoriteTrue(user, sort))
                .thenReturn(List.of(status2,status));

        List<MovieWithStatusDto> result = movieStatusServiceImpl.listFavorites(1L, "releaseDate", "asc");

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().releaseDate()).isEqualTo(LocalDate.of(2019, 1, 1));
        assertThat(result.getLast().releaseDate()).isEqualTo(LocalDate.of(2020, 1, 1));

        verify(userService).findById(1L);
        verify(sortFactory).buildSort("releaseDate", "asc");
        verify(movieStatusRepository).findByUserAndFavoriteTrue(user,sort);
    }

    @Test
    void whenFavoritesExistSortedByRating_thenReturnFavoritesOrderedByRating() {
        Sort sort = Sort.by(Sort.Direction.DESC, "movie.rating");

        when(userService.findById(1L)).thenReturn(user);
        when(sortFactory.buildSort("rating", "desc")).thenReturn(sort);
        when(movieStatusRepository.findByUserAndFavoriteTrue(user, sort))
                .thenReturn(List.of(status2,status));

        List<MovieWithStatusDto> result = movieStatusServiceImpl.listFavorites(1L, "rating", "desc");

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().rating()).isEqualTo(10.0);
        assertThat(result.getLast().rating()).isEqualTo(2.0);


        verify(userService).findById(1L);
        verify(sortFactory).buildSort("rating", "desc");
        verify(movieStatusRepository).findByUserAndFavoriteTrue(user,sort);
    }

    @Test
    void whenNoFavorites_thenReturnEmptyList() {
        Sort sort = Sort.by(Sort.Direction.ASC, "movie.releaseDate");

        when(userService.findById(1L)).thenReturn(user);
        when(sortFactory.buildSort("releaseDate", "asc")).thenReturn(sort);
        when(movieStatusRepository.findByUserAndFavoriteTrue(user, sort))
                .thenReturn(List.of());

        List<MovieWithStatusDto> result = movieStatusServiceImpl.listFavorites(1L, "releaseDate", "asc");

        assertThat(result).isEmpty();

        verify(userService).findById(1L);
        verify(sortFactory).buildSort("releaseDate", "asc");
        verify(movieStatusRepository).findByUserAndFavoriteTrue(user,sort);
    }

    @Test
    void whenSeenMoviesExist_thenReturnSeenMovies(){

        when(userService.findById(1L)).thenReturn(user);
        when(movieStatusRepository.findByUserAndSeen(user, true)).thenReturn(List.of(status,status2));

        List<MovieWithStatusDto> result = movieStatusServiceImpl.listSeenMovies(1L);
        assertThat(result).hasSize(2);
        assertThat(result.getFirst().title()).isEqualTo("Film A");
        assertThat(result.getFirst().seen()).isTrue();

        verify(userService).findById(1L);
        verify(movieStatusRepository).findByUserAndSeen(user,true);

    }

    @Test
    void whenNoSeenMovies_thenReturnEmptyList() {
        when(userService.findById(1L)).thenReturn(user);
        when(movieStatusRepository.findByUserAndSeen(user, true)).thenReturn(List.of());

        List<MovieWithStatusDto> result = movieStatusServiceImpl.listSeenMovies(1L);

        assertThat(result).isEmpty();

        verify(userService).findById(1L);
        verify(movieStatusRepository).findByUserAndSeen(user,true);
    }

    @Test
    void whenUnseenMoviesExist_thenReturnUnseenMovies(){
        when(userService.findById(1L)).thenReturn(user);
        when(movieRepository.findUnseenByUser(user)).thenReturn(List.of(movie3));

        List<MovieWithStatusDto> result = movieStatusServiceImpl.listUnseenMovies(1L);

        assertThat(result).hasSize(1);
        assertThat(result).extracting(MovieWithStatusDto::title).containsExactlyInAnyOrder("Film C");

        assertThat(result).allSatisfy(dto -> {assertThat(dto.seen()).isFalse(); assertThat(dto.favorite()).isFalse();});
        verify(userService).findById(1L);
        verify(movieRepository).findUnseenByUser(user);
    }

    @Test
    void whenNoUnseenMovies_thenReturnEmptyList() {
        when(userService.findById(1L)).thenReturn(user);
        when(movieRepository.findUnseenByUser(user)).thenReturn(List.of());

        List<MovieWithStatusDto> result = movieStatusServiceImpl.listUnseenMovies(1L);

        assertThat(result).isEmpty();

        verify(userService).findById(1L);
        verify(movieRepository).findUnseenByUser(user);

    }

}