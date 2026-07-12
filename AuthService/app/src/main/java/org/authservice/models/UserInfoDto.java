package org.authservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.authservice.entities.UserInfo;

@Getter
@Setter
@Builder
public class UserInfoDto  {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

}
