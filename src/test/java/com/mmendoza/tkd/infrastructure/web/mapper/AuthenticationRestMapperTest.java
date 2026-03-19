package com.mmendoza.tkd.infrastructure.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.mmendoza.tkd.core.model.User;
import com.mmendoza.tkd.core.model.auth.AuthTokens;
import com.mmendoza.tkd.model.LoginResponse;
import com.mmendoza.tkd.model.RegisterUserRequest;

public class AuthenticationRestMapperTest {

    private final AuthenticationRestMapper authenticationRestMapper = new AuthenticationRestMapper();

    @Test
    void shouldReturnLoginResponse() {
        LoginResponse loginResponse = authenticationRestMapper.toLoginResponse(new AuthTokens("jwt", "refreshToken"));
        assertEquals("jwt", loginResponse.getJwt());
        assertEquals("refreshToken", loginResponse.getRefreshToken());
    }

    @Test
    void shouldReturnUser() {
        User user = authenticationRestMapper.toUser(new RegisterUserRequest("username", "password", "email"));
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("email", user.getEmail());
    }
}
