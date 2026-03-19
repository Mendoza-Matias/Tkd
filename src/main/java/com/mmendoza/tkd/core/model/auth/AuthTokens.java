package com.mmendoza.tkd.core.model.auth;

public record AuthTokens(
        String jwt,
        String refreshToken) {

}
