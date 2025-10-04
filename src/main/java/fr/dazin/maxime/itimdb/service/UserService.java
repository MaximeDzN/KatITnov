package fr.dazin.maxime.itimdb.service;

import fr.dazin.maxime.itimdb.domain.User;

public interface UserService {
    public User findById(long id);
}
