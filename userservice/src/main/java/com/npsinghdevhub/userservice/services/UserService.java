package com.npsinghdevhub.userservice.services;

import com.npsinghdevhub.userservice.entities.UserInfo;
import com.npsinghdevhub.userservice.models.UserInfoDto;
import com.npsinghdevhub.userservice.repositries.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class UserService
{
    @Autowired
    private final UserRepository userRepository;

    public UserInfoDto createOrUpdateUser(UserInfoDto userInfoDto){

        UnaryOperator<UserInfo> updatingUser = existingUser -> {
            existingUser.setUserName(userInfoDto.getUserName());
            existingUser.setFirstName(userInfoDto.getFirstName());
            existingUser.setLastName(userInfoDto.getLastName());
            existingUser.setPhoneNumber(userInfoDto.getPhoneNumber());
            existingUser.setProfilePic(userInfoDto.getProfilePic());

            return userRepository.save(existingUser);
        };


        Supplier<UserInfo> createUser = () -> {
            return userRepository.save(userInfoDto.transformToUserInfo());
        };

        UserInfo userInfo = userRepository.findByUserId(userInfoDto.getUserId())
                .map(updatingUser)
                .orElseGet(createUser);
        return new UserInfoDto(
                userInfo.getUserId(),
                userInfo.getUserName(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhoneNumber(),
                userInfo.getProfilePic()
        );
    }

    public UserInfoDto getUser(String  userId) throws Exception{
        Optional<UserInfo> userInfoDtoOpt = userRepository.findByUserId(userId);
        if(userInfoDtoOpt.isEmpty()){
            throw new Exception("User not found");
        }
        UserInfo userInfo = userInfoDtoOpt.get();
        return new UserInfoDto(
                userInfo.getUserId(),
                userInfo.getUserName(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhoneNumber(),
                userInfo.getProfilePic()
        );
    }

}