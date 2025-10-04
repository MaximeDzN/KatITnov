package fr.dazin.maxime.itimdb.service;

import fr.dazin.maxime.itimdb.domain.Movie;
import fr.dazin.maxime.itimdb.exception.MovieNotFoundException;
import fr.dazin.maxime.itimdb.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie findById(long id) {
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }
}
