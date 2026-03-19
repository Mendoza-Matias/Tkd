package com.mmendoza.tkd.infrastructure.security.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mmendoza.tkd.core.model.AuthenticationTokens;
import com.mmendoza.tkd.core.model.Role;
import com.mmendoza.tkd.core.model.User;
import com.mmendoza.tkd.core.model.auth.AuthTokens;
import com.mmendoza.tkd.core.model.enums.RoleName;
import com.mmendoza.tkd.core.service.IAuthenticationService;
import com.mmendoza.tkd.core.service.IAuthenticationTokensService;
import com.mmendoza.tkd.core.service.IRoleService;
import com.mmendoza.tkd.core.service.IUserService;
import com.mmendoza.tkd.infrastructure.security.exception.AuthException;
import com.mmendoza.tkd.infrastructure.security.jwt.JwtService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserService iUserService;
    private final IRoleService iRoleService;
    private final IAuthenticationTokensService iAuthenticationTokensService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Integer register(User user) {
        Role role = iRoleService.findByName(RoleName.USER.name());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(role);
        user.activate();

        return iUserService.save(user);
    }

    @Override
    public AuthTokens login(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));

        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();

        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveTokens(user.getUserId(), jwt, refreshToken);

        return new AuthTokens(jwt, refreshToken);
    }

    @Override
    @Transactional
    public AuthTokens refreshToken(String refreshToken) {
        AuthenticationTokens authenticationTokens = iAuthenticationTokensService.findByRefreshToken(refreshToken);
        if (!authenticationTokens.getIsActive()) {
            throw new AuthException("Token no es valido", HttpStatus.UNAUTHORIZED);
        }

        User user = iUserService.findById(authenticationTokens.getUserId());
        CustomUserDetail userDetail = new CustomUserDetail(user);

        // Si el token no es valido o esta expirado
        if (!jwtService.isRefreshTokenValid(refreshToken, userDetail)) {
            iAuthenticationTokensService.invalidate(refreshToken);
            throw new AuthException("Token expirado o no valido", HttpStatus.UNAUTHORIZED);
        }

        String newAccessToken = jwtService.generateToken(userDetail);
        String newRefreshToken = jwtService.generateRefreshToken(userDetail);

        iAuthenticationTokensService.invalidate(refreshToken); // Invalido el token anterior
        saveTokens(user.getId(), newAccessToken, newRefreshToken); // Guardo los nuevos tokens

        return new AuthTokens(newAccessToken, newRefreshToken);
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        iAuthenticationTokensService.invalidate(refreshToken);
    }

    private void saveTokens(Integer userId, String jwt, String refreshToken) {
        AuthenticationTokens authenticationTokens = AuthenticationTokens.builder()
                .userId(userId)
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
        iAuthenticationTokensService.save(authenticationTokens);
    }
}
