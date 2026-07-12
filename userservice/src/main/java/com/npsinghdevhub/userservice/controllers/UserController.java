package com.npsinghdevhub.userservice.controllers;

import com.npsinghdevhub.userservice.models.UserInfoDto;
import com.npsinghdevhub.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/v1/createOrUpdateUser")
    public ResponseEntity<UserInfoDto> createUpdateUser(@RequestBody UserInfoDto userInfoDto, @RequestHeader("X-USER-ID") String userId){
        try{
            userInfoDto.setUserId(userId);
            UserInfoDto user= userService.createOrUpdateUser(userInfoDto);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user/v1/getUser")
    public ResponseEntity<UserInfoDto> getUser(@RequestHeader("X-USER-ID") String userId){
        try{
            UserInfoDto user= userService.getUser(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
