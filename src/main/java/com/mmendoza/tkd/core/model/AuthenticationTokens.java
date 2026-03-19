package com.mmendoza.tkd.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authentication_tokens")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AuthenticationTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String accessToken;
    private String refreshToken;
    private Boolean isActive;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void activate() {
        this.isActive = true;
    }

    public void invalidate() {
        this.isActive = false;
    }
}
