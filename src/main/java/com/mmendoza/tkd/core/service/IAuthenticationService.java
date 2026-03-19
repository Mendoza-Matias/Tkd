package com.mmendoza.tkd.core.service;

import com.mmendoza.tkd.core.model.User;
import com.mmendoza.tkd.core.model.auth.AuthTokens;

public interface IAuthenticationService {
    Integer register(User user);

    AuthTokens login(String email, String password);

    AuthTokens refreshToken(String refreshToken);

    void logout(String refreshToken);
}
