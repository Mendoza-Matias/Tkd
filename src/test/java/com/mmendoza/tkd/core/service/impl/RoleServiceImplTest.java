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
<<<<<<< Updated upstream
=======
import org.springframework.http.HttpStatus;
>>>>>>> Stashed changes

import com.mmendoza.tkd.core.exception.BussinesException;
import com.mmendoza.tkd.core.model.Role;
import com.mmendoza.tkd.core.repository.IRoleRepository;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private IRoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    private Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .id(1)
                .name("ROLE_USER")
                .build();
    }

    @Test
    void shouldFindByName() {
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
        assertEquals(role, roleServiceImpl.findByName(role.getName()));
    }

    @Test
    void shouldExceptionWhenFindByNameReturnEmpty() {
        BussinesException exception = assertThrows(BussinesException.class,
                () -> roleServiceImpl.findByName(role.getName()));
        assertEquals("Rol no encontrado", exception.getMessage());
<<<<<<< Updated upstream
=======
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
>>>>>>> Stashed changes
    }
}
