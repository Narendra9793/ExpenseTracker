package org.authservice.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.authservice.entities.UserInfo;
import org.authservice.eventProducer.UserInfoEvent;
import org.authservice.eventProducer.UserInfoProducer;
import org.authservice.models.UserInfoDto;
import org.authservice.repositries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserInfoProducer userInfoProducer;


    public String getUserByUsername( String username)  {
        return Optional.of(userRepository.findByEmail(username)).map(UserInfo::getUserId).orElse(null);
    }
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user=this.userRepository.findByEmail(email);
        if(user == null){
            throw new RuntimeException("Could not found user.");
        }
        return new CustomUserDetails(user);
    }

    public UserInfo CheckIfUserAlreadyExisted(UserInfoDto userInfoDto){
        return this.userRepository.findByEmail(userInfoDto.getEmail());
    }

    public Boolean issignedUpUser(UserInfoDto userInfoDto){
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(CheckIfUserAlreadyExisted(userInfoDto))) return false;

        String userId = UUID.randomUUID().toString();
        this.userRepository.save(new UserInfo(userId, userInfoDto.getEmail(), userInfoDto.getPassword(), new HashSet<>()));
        userInfoProducer.sendEventToKafka(userInfoEventToPublish(userInfoDto, userId));
        return true;
    }

    private UserInfoEvent userInfoEventToPublish(UserInfoDto userInfoDto, String userId){
        return UserInfoEvent.builder()
                .userId(userId)
                .build();
    }

}
