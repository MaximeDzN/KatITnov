package fr.dazin.maxime.itimdb.service;

import fr.dazin.maxime.itimdb.domain.User;
import fr.dazin.maxime.itimdb.exception.UserNotFoundException;
import fr.dazin.maxime.itimdb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void whenUserExists_thenFindByIdReturnsUser() {
        User user = User.builder().id(1L).userName("Alice").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userServiceImpl.findById(1L);
        assertEquals(user, foundUser);
    }

    @Test
    void whenUserDoesNotExist_thenThrowException(){
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->userServiceImpl.findById(10L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with id")
                .hasMessageContaining("10");
    }

}