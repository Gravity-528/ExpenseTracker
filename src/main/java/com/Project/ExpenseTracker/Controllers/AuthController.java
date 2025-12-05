package com.Project.ExpenseTracker.Controllers;

import com.Project.ExpenseTracker.Response.JwtResponse;
import com.Project.ExpenseTracker.entities.RefreshTokens;
import com.Project.ExpenseTracker.models.UserInfoDto;
import com.Project.ExpenseTracker.service.JwtService;
import com.Project.ExpenseTracker.service.RefreshTokenService;
import com.Project.ExpenseTracker.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity SignUp(@RequestBody UserInfoDto userInfoDto){
        try{
            Boolean isSignUped=userDetailService.signupUser(userInfoDto);
            if(Boolean.FALSE.equals(isSignUped)){
                return new ResponseEntity<>("Already exist", HttpStatus.BAD_REQUEST);
            }
            RefreshTokens refreshTokens=refreshTokenService.createRefreshTokens(userInfoDto.getUserName());
            String jwtToken=jwtService.GeneratedToken(userInfoDto.getUserName());
            return new ResponseEntity<>(JwtResponse.builder().accessToken(jwtToken).token(refreshTokens.getRefreshToken()).build(),HttpStatus.OK);
        }catch(Exception e){
           return new ResponseEntity<>("Exception in our service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
