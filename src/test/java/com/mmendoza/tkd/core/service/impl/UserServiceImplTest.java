package com.mmendoza.tkd.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mmendoza.tkd.core.exception.BussinesException;
import com.mmendoza.tkd.core.model.User;
import com.mmendoza.tkd.core.repository.IUserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private IUserRepository iUserRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .username("username")
                .password("password")
                .email("email")
                .build();
    }

    @Test
    void shouldSaveUser() {
        when(iUserRepository.save(user)).thenReturn(user);
        assertEquals(1, userServiceImpl.save(user));
    }

    @Test
    void shouldFindByEmail() {
        when(iUserRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertEquals(user, userServiceImpl.findByEmail(user.getEmail()));
    }

    @Test
    void shouldFindById() {
        when(iUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertEquals(user, userServiceImpl.findById(user.getId()));
    }

    @Test
    void shouldExceptionWhenFindByEmailReturnEmpty() {
        BussinesException exception = assertThrows(BussinesException.class,
                () -> userServiceImpl.findByEmail(user.getEmail()));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void shouldExceptionWhenFindByIdReturnEmpty() {
        BussinesException exception = assertThrows(BussinesException.class,
                () -> userServiceImpl.findById(user.getId()));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }
}
