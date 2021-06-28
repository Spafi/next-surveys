package com.spaf.surveys.security.registration.email;

public interface EmailSender {
    void send(String to, String email);
}