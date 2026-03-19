package com.mmendoza.tkd.core.service;

import com.mmendoza.tkd.core.model.AuthenticationTokens;

public interface IAuthenticationTokensService {
    void save(AuthenticationTokens authenticationTokens);

    void invalidate(String refreshToken);

    AuthenticationTokens findByRefreshToken(String refreshToken);
}
