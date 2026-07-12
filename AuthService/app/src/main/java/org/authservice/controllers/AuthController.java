package org.authservice.controllers;

import lombok.AllArgsConstructor;
import org.authservice.entities.RefreshToken;
import org.authservice.models.UserInfoDto;
import org.authservice.responses.JwtResponseDto;
import org.authservice.services.JwtService;
import org.authservice.services.RefreshTokenService;
import org.authservice.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@AllArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;



    @PostMapping("/auth/v1/signup")
    public ResponseEntity signup(@RequestBody UserInfoDto userInfoDto){
        try {
            Boolean isSignedUp = userDetailsService.issignedUpUser(userInfoDto);
            if(Boolean.FALSE.equals(isSignedUp)){
                return new ResponseEntity<>("User already Exists!", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getEmail());
            String accessToken = jwtService.generateToken(userInfoDto.getEmail());

            return new ResponseEntity<>(JwtResponseDto.builder().accessToken(accessToken ).refreshToken(refreshToken.getToken()).build(), HttpStatus.OK);

        }
        catch (Exception ex){
            return new ResponseEntity<>("There is Exception in UserService!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ping")
    public ResponseEntity<String> pong(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        if(authentication != null && authentication.isAuthenticated()){
            String userId = userDetailsService.getUserByUsername(authentication.getName());
            if(Objects.nonNull(userId)){
                return ResponseEntity.ok(userId);
            }
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }
}
