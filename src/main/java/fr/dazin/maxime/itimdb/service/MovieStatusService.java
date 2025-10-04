package fr.dazin.maxime.itimdb.service;

import fr.dazin.maxime.itimdb.web.dto.MovieWithStatusDto;

import java.util.List;

public interface MovieStatusService {
    void updateFavorite(long userId, long movieId, boolean favorite);
    void updateSeen(long userId, long movieId);
    List<MovieWithStatusDto> listFavorites(long userId, String sortBy, String order);
    List<MovieWithStatusDto> listSeenMovies(long userId);
    List<MovieWithStatusDto> listUnseenMovies(long userId);
}
