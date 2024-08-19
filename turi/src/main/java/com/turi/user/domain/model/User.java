package com.turi.user.domain.model;

import com.turi.user.infrastructure.adapter.repository.UserEntity;
import lombok.*;

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

    public static User of(final UserEntity entity)
    {
        return User.builder()
                .withUserId(entity.getUserId())
                .withUsername(entity.getUsername())
                .withEmail(entity.getEmail())
                .withPassword(entity.getPassword())
                .build();
    }
}
