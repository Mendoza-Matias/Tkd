package com.mmendoza.tkd.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmendoza.tkd.core.model.AuthenticationTokens;

@Repository
public interface IAuthenticationTokensRepository extends JpaRepository<AuthenticationTokens, Integer> {
    Optional<AuthenticationTokens> findByRefreshToken(String refreshToken);
}
