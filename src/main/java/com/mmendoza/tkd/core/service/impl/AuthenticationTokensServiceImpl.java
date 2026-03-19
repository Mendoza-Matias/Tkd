package com.mmendoza.tkd.core.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mmendoza.tkd.core.exception.BussinesException;
import com.mmendoza.tkd.core.model.AuthenticationTokens;
import com.mmendoza.tkd.core.repository.IAuthenticationTokensRepository;
import com.mmendoza.tkd.core.service.IAuthenticationTokensService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationTokensServiceImpl implements IAuthenticationTokensService {

    private final IAuthenticationTokensRepository iAuthenticationTokensRepository;

    @Override
    public void save(AuthenticationTokens authenticationTokens) {
        authenticationTokens.activate(); // Set isActive to true
        iAuthenticationTokensRepository.save(authenticationTokens);
    }

    @Transactional
    @Override
    public void invalidate(String refreshToken) {
        AuthenticationTokens authenticationTokens = findByRefreshToken(refreshToken);
        authenticationTokens.invalidate();
    }

    @Override
    public AuthenticationTokens findByRefreshToken(String refreshToken) {
        return iAuthenticationTokensRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BussinesException("Token no encontrado", HttpStatus.NOT_FOUND));
    }
}
