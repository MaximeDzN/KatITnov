package fr.dazin.maxime.itimdb.web.dto;

import fr.dazin.maxime.itimdb.domain.Movie;
import fr.dazin.maxime.itimdb.domain.MovieStatus;

import java.time.LocalDate;

public record MovieWithStatusDto(Long id, String title, String description, double rating, LocalDate releaseDate, boolean favorite, boolean seen) {
    public static MovieWithStatusDto from(MovieStatus movieStatus) {
        Movie m = movieStatus.getMovie();
        return new MovieWithStatusDto(m.getId(), m.getTitle(), m.getDescription(), m.getRating(), m.getReleaseDate(), movieStatus.isFavorite(), movieStatus.isSeen());
    }
}
