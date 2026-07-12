package org.authservice.requests;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class AuthRequestDto {
    private String email;
    private String password;
}
