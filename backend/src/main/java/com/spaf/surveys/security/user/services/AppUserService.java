package com.spaf.surveys.security.user.services;

import com.spaf.surveys.security.registration.token.ConfirmationToken;
import com.spaf.surveys.security.registration.token.ConfirmationTokenService;
import com.spaf.surveys.security.user.models.AppUser;
import com.spaf.surveys.security.user.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private static final String USERNAME_NOT_FOUND_MESSAGE = "User with email %s not found!";

    private final AppUserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE, email)));
    }

    public String signUpUser(AppUser user) {
        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if (userExists) throw new IllegalStateException("Email already taken");
//        TODO: if email not confirmed, send confirmation email, or resend confirmation email if same user that registered

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user

        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

//        TODO: Send email

        return token;
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    public AppUser findAppUserById(UUID id) {
//        TODO: Null check
        return userRepository.findById(id).get();
    }
}
