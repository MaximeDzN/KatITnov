package fr.dazin.maxime.itimdb.service;

import fr.dazin.maxime.itimdb.domain.Movie;

public interface MovieService {
    public Movie findById(long id);
}
