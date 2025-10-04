package fr.dazin.maxime.itimdb.service;

import fr.dazin.maxime.itimdb.domain.Movie;
import fr.dazin.maxime.itimdb.domain.MovieStatus;
import fr.dazin.maxime.itimdb.domain.User;
import fr.dazin.maxime.itimdb.repository.MovieRepository;
import fr.dazin.maxime.itimdb.repository.MovieStatusRepository;
import fr.dazin.maxime.itimdb.service.util.SortFactory;
import fr.dazin.maxime.itimdb.web.dto.MovieWithStatusDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieStatusServiceImpl implements MovieStatusService {

    private final MovieStatusRepository movieStatusRepository;
    private final MovieService movieService;
    private final UserService userService;
    private final SortFactory sortFactory;
    private final MovieRepository movieRepository;


    public MovieStatusServiceImpl(MovieStatusRepository movieStatusRepository, MovieService movieService, UserService userService, SortFactory sortFactory, MovieRepository movieRepository) {
        this.movieStatusRepository = movieStatusRepository;
        this.movieService = movieService;
        this.userService = userService;
        this.sortFactory = sortFactory;
        this.movieRepository = movieRepository;
    }

    @Override
    @Transactional
    public void updateFavorite(long userId, long movieId, boolean favorite) {
        MovieStatus movieStatus = getOrCreateStatus(userId, movieId);
        movieStatus.setSeen(true);
        movieStatus.setFavorite(favorite);
        movieStatusRepository.save(movieStatus);
    }

    @Override
    @Transactional
    public void updateSeen(long userId, long movieId) {
        MovieStatus movieStatus = getOrCreateStatus(userId, movieId);
        movieStatus.setSeen(true);
        movieStatusRepository.save(movieStatus);
    }

    @Override
    public List<MovieWithStatusDto> listFavorites(long userId, String sortBy, String order) {
        User user = userService.findById(userId);
        Sort sort = sortFactory.buildSort(sortBy, order);
        return movieStatusRepository.findByUserAndFavoriteTrue(user, sort).stream().map(MovieWithStatusDto::from).toList();
    }

    @Override
    public List<MovieWithStatusDto> listSeenMovies(long userId) {
        User user = userService.findById(userId);
        return movieStatusRepository.findByUserAndSeen(user, true).stream().map(MovieWithStatusDto::from).toList();
    }

    @Override
    public List<MovieWithStatusDto> listUnseenMovies(long userId) {
        User user = userService.findById(userId);
        return movieRepository.findUnseenByUser(user).stream()
                .map(movie ->
                        new MovieWithStatusDto(
                                movie.getId(),
                                movie.getTitle(),
                                movie.getDescription(),
                                movie.getRating(),
                                movie.getReleaseDate(),
                                false,
                                false))
                .toList();
    }

    private MovieStatus getOrCreateStatus(long userId, long movieId) {
        User user = userService.findById(userId);
        Movie movie = movieService.findById(movieId);
        return movieStatusRepository
                .findByUserAndMovie(user, movie)
                .orElse(MovieStatus
                        .builder()
                        .user(user)
                        .movie(movie)
                        .build());
    }


}
