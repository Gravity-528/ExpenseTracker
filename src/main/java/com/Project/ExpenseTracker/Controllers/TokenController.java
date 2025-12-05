package com.Project.ExpenseTracker.Controllers;

import com.Project.ExpenseTracker.Request.AuthRequest;
import com.Project.ExpenseTracker.Request.RefreshTokenRequest;
import com.Project.ExpenseTracker.Response.JwtResponse;
import com.Project.ExpenseTracker.entities.RefreshTokens;
import com.Project.ExpenseTracker.entities.UserInfo;
import com.Project.ExpenseTracker.service.JwtService;
import com.Project.ExpenseTracker.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("auth/v1/login")
    public ResponseEntity AuthenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshTokens refreshTokens=refreshTokenService.createRefreshTokens(authRequest.getUsername());
            return new ResponseEntity<>(JwtResponse.builder().accessToken(jwtService.GeneratedToken(authRequest.getUsername())).token(refreshTokens.getRefreshToken()).build(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Exception in our service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("auth/v1/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokens::getUserInfo)
                .map(userInfo -> {
                    String accessToken=jwtService.GeneratedToken(userInfo.getUsername());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken()).build();
                }).orElseThrow(()->new RuntimeException("RefreshToken is not on DB"));

    }
}
