package org.authservice.controllers;


import lombok.AllArgsConstructor;
import org.authservice.entities.RefreshToken;
import org.authservice.requests.AuthRequestDto;
import org.authservice.requests.RefreshTokenRequestDto;
import org.authservice.responses.JwtResponseDto;
import org.authservice.services.JwtService;
import org.authservice.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/auth/v1/login")
    public ResponseEntity authenticationAndGetToken(@RequestBody AuthRequestDto authRequestDto){
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDto.getEmail());
            return new  ResponseEntity<>(JwtResponseDto.builder().accessToken(jwtService.generateToken(authRequestDto.getEmail())).refreshToken(refreshToken.getToken()).build(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/v1/refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){

        return  refreshTokenService.findByToken( refreshTokenRequestDto.getToken())
                .map(refreshTokenService::verifyRefreshToken)
                .map(RefreshToken::getUserInfo)
                .map(userInfo ->{
                            String accessToken = jwtService.generateToken(userInfo.getEmail());
                            return JwtResponseDto.builder().refreshToken(refreshTokenRequestDto.getToken())
                                    .accessToken(accessToken ).build();
                        }
                ).orElseThrow(()->new RuntimeException("The Refresh token is not in Data Base!"));
    }
}
