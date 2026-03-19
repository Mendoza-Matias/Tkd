package com.mmendoza.tkd.infrastructure.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mmendoza.tkd.core.model.auth.AuthTokens;
import com.mmendoza.tkd.core.service.IAuthenticationService;
import com.mmendoza.tkd.infrastructure.web.mapper.AuthenticationRestMapper;
import com.mmendoza.tkd.model.LoginResponse;
import com.mmendoza.tkd.model.RefreshTokenRequest;
import com.mmendoza.tkd.model.LoginRequest;
import com.mmendoza.tkd.model.RegisterUserRequest;

@ExtendWith(MockitoExtension.class)
public class AuthenticationApiControllerTest {

    @Mock
    private IAuthenticationService iAuthenticationService;

    @Mock
    private AuthenticationRestMapper authenticationRestMapper;

    @InjectMocks
    private AuthenticationApiController authenticationApiController;

    private RegisterUserRequest registerUserRequest;
    private LoginRequest loginRequest;
    private RefreshTokenRequest refreshTokenRequest;
    private AuthTokens authTokens;
    private LoginResponse loginResponse;

    @BeforeEach
    void setUp() {
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("email");
        registerUserRequest.setPassword("password");
        registerUserRequest.setUsername("username");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("email");
        loginRequest.setPassword("password");

        refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken("refreshToken");

        authTokens = new AuthTokens("jwt", "refreshToken");

        loginResponse = new LoginResponse();
        loginResponse.setJwt("jwt");
        loginResponse.setRefreshToken("refreshToken");
    }

    @Test
    void shouldRegisterUser() {
        when(iAuthenticationService.register(authenticationRestMapper.toUser(registerUserRequest)))
                .thenReturn(1);
        ResponseEntity<Integer> response = authenticationApiController.registerUser(registerUserRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    void shouldLogin() {
        when(iAuthenticationService.login(loginRequest.getEmail(), loginRequest.getPassword())).thenReturn(authTokens);
        when(authenticationRestMapper.toLoginResponse(authTokens)).thenReturn(loginResponse);
        ResponseEntity<LoginResponse> response = authenticationApiController.login(loginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponse, response.getBody());
    }

    @Test
    void shouldLogout() {
        ResponseEntity<Void> response = authenticationApiController.logout(refreshTokenRequest);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldRefreshToken() {
        when(iAuthenticationService.refreshToken(refreshTokenRequest.getRefreshToken())).thenReturn(authTokens);
        when(authenticationRestMapper.toLoginResponse(authTokens)).thenReturn(loginResponse);
        ResponseEntity<LoginResponse> response = authenticationApiController.refreshToken(refreshTokenRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponse, response.getBody());
    }
}
