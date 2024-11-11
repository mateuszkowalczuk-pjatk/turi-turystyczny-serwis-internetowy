package com.turi.user.infrastructure.adapter.repository;

import com.turi.user.domain.exception.InvalidUserException;
import com.turi.user.domain.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
@Builder(setterPrefix = "with")
public final class UserEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 6661538792136369331L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userId;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "passwordresetcode")
    private Integer passwordResetCode;

    @Column(name = "passwordresettoken")
    private String passwordResetToken;

    @Column(name = "passwordresetexpiresat")
    private LocalDateTime passwordResetExpiresAt;

    public static UserEntity of(final User user)
    {
        if (!validation(user))
        {
            throw new InvalidUserException();
        }

        return UserEntity.builder()
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withPassword(user.getPassword())
                .withPasswordResetCode(user.getPasswordResetCode())
                .withPasswordResetToken(user.getPasswordResetToken())
                .withPasswordResetExpiresAt(user.getPasswordResetExpiresAt())
                .build();
    }

    private static boolean validation(final User user)
    {
        return user.getUsername() != null
                && user.getEmail() != null
                && user.getPassword() != null;
    }
}
