package org.authservice.services;

import org.authservice.entities.RefreshToken;
import org.authservice.entities.UserInfo;
import org.authservice.repositries.RefreshTokenRepository;
import org.authservice.repositries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    public RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public UserRepository userRepository;


    public RefreshToken createRefreshToken(String email) {
        UserInfo user = userRepository.findByEmail(email);
        RefreshToken refreshToken = refreshTokenRepository.findByUserInfo(user).orElse(new RefreshToken());

        refreshToken.setUserInfo(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(
                Instant.now().plusMillis(60 * 60 * 1000));

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyRefreshToken(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            this.refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + "Refresh Token is expired, Longin again.");
        }
        return refreshToken;
    }
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
}
