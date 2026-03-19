package com.mmendoza.tkd.infrastructure.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mmendoza.tkd.core.model.User;
import com.mmendoza.tkd.core.model.auth.AuthTokens;
import com.mmendoza.tkd.model.LoginResponse;
import com.mmendoza.tkd.model.RegisterUserRequest;

public class AuthenticationRestMapperTest {

    private final AuthenticationRestMapper authenticationRestMapper = new AuthenticationRestMapper();

    private AuthTokens authTokens;

    private RegisterUserRequest registerUserRequest;

    @BeforeEach
    void setUp() {
        authTokens = new AuthTokens("jwt", "refreshToken");
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        registerUserRequest.setEmail("email");
    }

    @Test
    void shouldReturnLoginResponse() {
        LoginResponse loginResponse = authenticationRestMapper.toLoginResponse(authTokens);
        assertEquals(authTokens.jwt(), loginResponse.getJwt());
        assertEquals(authTokens.refreshToken(), loginResponse.getRefreshToken());
    }

    @Test
    void shouldReturnUser() {
        User user = authenticationRestMapper.toUser(registerUserRequest);
        assertEquals(registerUserRequest.getUsername(), user.getUsername());
        assertEquals(registerUserRequest.getPassword(), user.getPassword());
        assertEquals(registerUserRequest.getEmail(), user.getEmail());
    }
}
