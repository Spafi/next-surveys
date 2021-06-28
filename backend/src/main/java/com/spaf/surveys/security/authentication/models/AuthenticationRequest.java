package com.spaf.surveys.security.authentication.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class AuthenticationRequest {

    private String email;
    private String password;


}
