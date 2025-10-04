package fr.dazin.maxime.itimdb.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(long id) {
        super("User not found with id " + id);
    }
}
