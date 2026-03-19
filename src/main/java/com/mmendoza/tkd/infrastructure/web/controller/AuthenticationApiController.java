package com.mmendoza.tkd.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mmendoza.tkd.api.AuthApi;
import com.mmendoza.tkd.core.model.auth.AuthTokens;
import com.mmendoza.tkd.core.service.IAuthenticationService;
import com.mmendoza.tkd.infrastructure.web.mapper.AuthenticationRestMapper;
import com.mmendoza.tkd.model.LoginResponse;
import com.mmendoza.tkd.model.RefreshTokenRequest;
import com.mmendoza.tkd.model.LoginRequest;
import com.mmendoza.tkd.model.RegisterUserRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationApiController implements AuthApi {

    private final IAuthenticationService iAuthenticationService;
    private final AuthenticationRestMapper authenticationRestMapper;

    @Override
    public ResponseEntity<Integer> registerUser(RegisterUserRequest registerUserRequest) {
        Integer userId = iAuthenticationService.register(authenticationRestMapper.toUser(registerUserRequest));
        return ResponseEntity.ok(userId);
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        AuthTokens authTokens = iAuthenticationService.login(loginRequest.getEmail(),
                loginRequest.getPassword());
        return ResponseEntity.ok(authenticationRestMapper.toLoginResponse(authTokens));
    }

    @Override
    public ResponseEntity<Void> logout(RefreshTokenRequest refreshTokenRequest) {
        iAuthenticationService.logout(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<LoginResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        AuthTokens authTokens = iAuthenticationService.refreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(authenticationRestMapper.toLoginResponse(authTokens));
    }

}
