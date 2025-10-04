package fr.dazin.maxime.itimdb.exception;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(long id) {
        super("Movie not found with id " + id);
    }
}
