package fr.dazin.maxime.itimdb.service;

import fr.dazin.maxime.itimdb.domain.User;
import fr.dazin.maxime.itimdb.exception.UserNotFoundException;
import fr.dazin.maxime.itimdb.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }
}
