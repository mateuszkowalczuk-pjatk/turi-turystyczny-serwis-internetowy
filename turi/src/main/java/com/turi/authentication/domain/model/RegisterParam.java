package com.turi.authentication.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class RegisterParam
{
    private String username;
    private String email;
    private String password;
}
