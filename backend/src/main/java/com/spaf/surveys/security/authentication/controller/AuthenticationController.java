package com.spaf.surveys.security.authentication.controller;

import com.spaf.surveys.security.authentication.models.AuthenticationRequest;
import com.spaf.surveys.security.authentication.models.AuthenticationResponse;
import com.spaf.surveys.security.user.models.AppUser;
import com.spaf.surveys.security.user.services.AppUserService;
import com.spaf.surveys.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;


    @RequestMapping(value = "/api/v1/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        final String role = ((AppUser) userDetails).getRole().name();

        final UUID userId = ((AppUser) userDetails).getId();

        final String firstName = ((AppUser) userDetails).getFirstName();

        return ResponseEntity.ok(new AuthenticationResponse(jwt, role, userId, firstName));
    }

}
