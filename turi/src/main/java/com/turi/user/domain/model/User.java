package com.turi.user.domain.model;

import com.turi.user.infrastructure.adapter.repository.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class User
{
    private Long userId;
    private String username;
    private String email;
    private String password;
    private Integer passwordResetCode;
    private String passwordResetToken;
    private LocalDateTime passwordResetExpiresAt;

    public static User of(final UserEntity entity)
    {
        return User.builder()
                .withUserId(entity.getUserId())
                .withUsername(entity.getUsername())
                .withEmail(entity.getEmail())
                .withPassword(entity.getPassword())
                .withPasswordResetCode(entity.getPasswordResetCode())
                .withPasswordResetToken(entity.getPasswordResetToken())
                .withPasswordResetExpiresAt(entity.getPasswordResetExpiresAt())
                .build();
    }
}
