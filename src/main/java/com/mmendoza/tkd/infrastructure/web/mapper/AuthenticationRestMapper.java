package com.mmendoza.tkd.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import com.mmendoza.tkd.core.model.User;
import com.mmendoza.tkd.core.model.auth.AuthTokens;
import com.mmendoza.tkd.model.LoginResponse;
import com.mmendoza.tkd.model.RegisterUserRequest;

@Component
public class AuthenticationRestMapper {

    public LoginResponse toLoginResponse(AuthTokens authTokens) {
        return new LoginResponse()
                .jwt(authTokens.jwt())
                .refreshToken(authTokens.refreshToken());
    }

    public User toUser(RegisterUserRequest registerUserRequest) {
        return User.builder()
                .username(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .email(registerUserRequest.getEmail())
                .build();
    }
}
