package com.Project.ExpenseTracker.service;

import com.Project.ExpenseTracker.entities.RefreshTokens;
import com.Project.ExpenseTracker.entities.UserInfo;
import com.Project.ExpenseTracker.repository.RefreshTokenRepository;
import com.Project.ExpenseTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class RefreshTokenService {
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    public RefreshTokens createRefreshTokens(String username){
        UserInfo userInfoExtracted =userRepository.findByUsername(username);
        RefreshTokens refreshToken=RefreshTokens.builder()
                .userInfo(userInfoExtracted)
                .refreshToken(UUID.randomUUID().toString())
                .expiry(Instant.now().plusMillis(6000000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshTokens verifyExpiration(RefreshTokens token){
        if(token.getExpiry().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getRefreshToken()+"RefreshToken is expired make a new login");
        }
        return token;
    }

    public Optional<RefreshTokens> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
}
