package com.spaf.surveys.security.authentication.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private final String jwt;
    private final String role;
    private final UUID userId;
    private final String firstName;
}
