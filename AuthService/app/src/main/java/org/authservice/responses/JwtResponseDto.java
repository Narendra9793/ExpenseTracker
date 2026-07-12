package org.authservice.responses;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class JwtResponseDto {
    private String accessToken;
    private String refreshToken;
}
