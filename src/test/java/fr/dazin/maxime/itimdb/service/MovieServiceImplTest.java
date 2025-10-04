package fr.dazin.maxime.itimdb.service;

import fr.dazin.maxime.itimdb.domain.Movie;
import fr.dazin.maxime.itimdb.exception.MovieNotFoundException;
import fr.dazin.maxime.itimdb.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieServiceImpl;

    @Test
    void whenMovieExists_thenFindByIdReturnsMovie() {

        Movie movie = Movie.builder().id(1L).title("film 1").description("super film").releaseDate(LocalDate.of(1970,1,1)).build();
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Movie foundMovie = movieServiceImpl.findById(1L);

        assertThat(foundMovie.getTitle()).isEqualTo("film 1");
        assertThat(foundMovie.getDescription()).isEqualTo("super film");
        assertThat(foundMovie.getReleaseDate()).isEqualTo(LocalDate.of(1970,1,1));
    }

    @Test
    void whenMovieNotExists_thenThrowException(){
        when(movieRepository.findById(10L)).thenReturn(Optional.empty());
        assertThatThrownBy(()->movieServiceImpl.findById(10L))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessageContaining("Movie not found with id")
                .hasMessageContaining("10");
    }

}