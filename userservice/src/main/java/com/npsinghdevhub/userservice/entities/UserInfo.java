package com.npsinghdevhub.userservice.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfo {
    @Id
    private String userId;

    @Column(nullable = true, unique = true)
    private String userName;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    @Column(nullable = true)
    private Long phoneNumber;


    private String profilePic;


}
