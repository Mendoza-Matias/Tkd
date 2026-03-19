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
import com.mmendoza.tkd.core.model.AuthenticationTokens;
import com.mmendoza.tkd.core.repository.IAuthenticationTokensRepository;

@ExtendWith(MockitoExtension.class)
public class AuthenticationTokensServiceImplTest {

    @Mock
    private IAuthenticationTokensRepository iAuthenticationTokensRepository;

    @InjectMocks
    private AuthenticationTokensServiceImpl authenticationTokensServiceImpl;

    private AuthenticationTokens authenticationTokens;

    @BeforeEach
    void setUp() {
        authenticationTokens = AuthenticationTokens.builder()
                .id(1)
                .accessToken("jwt")
                .refreshToken("refreshToken")
                .build();
    }

    @Test
    void shouldSave() {
        authenticationTokensServiceImpl.save(authenticationTokens);
    }

    @Test
    void shouldInvalidate() {
        when(iAuthenticationTokensRepository.findByRefreshToken(authenticationTokens.getRefreshToken()))
                .thenReturn(Optional.of(authenticationTokens));
        authenticationTokensServiceImpl.invalidate(authenticationTokens.getRefreshToken());
    }

    @Test
    void shouldExceptionWhenInvalidateReturnEmpty() {
        BussinesException exception = assertThrows(BussinesException.class,
                () -> authenticationTokensServiceImpl.invalidate(authenticationTokens.getRefreshToken()));
        assertEquals("Token no encontrado", exception.getMessage());
    }

    @Test
    void shouldFindByRefreshToken() {
        when(iAuthenticationTokensRepository.findByRefreshToken(authenticationTokens.getRefreshToken()))
                .thenReturn(Optional.of(authenticationTokens));
        assertEquals(authenticationTokens,
                authenticationTokensServiceImpl.findByRefreshToken(authenticationTokens.getRefreshToken()));
    }

    @Test
    void shouldExceptionWhenFindByRefreshTokenReturnEmpty() {
        BussinesException exception = assertThrows(BussinesException.class,
                () -> authenticationTokensServiceImpl.findByRefreshToken(authenticationTokens.getRefreshToken()));
        assertEquals("Token no encontrado", exception.getMessage());
    }
}
