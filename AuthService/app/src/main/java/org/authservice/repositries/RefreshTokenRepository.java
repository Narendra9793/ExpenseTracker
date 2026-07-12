package org.authservice.repositries;

import org.authservice.entities.RefreshToken;
import org.authservice.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

     Optional<RefreshToken> findByToken(String token) ;

    Optional<RefreshToken> findByUserInfo(UserInfo user);
}
