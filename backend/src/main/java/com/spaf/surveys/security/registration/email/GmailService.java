package com.spaf.surveys.security.registration.email;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class GmailService implements EmailSender {

    @Override
    @Async
    public void send(String to, String email) {


        // Sender's email ID needs to be mentioned
        String from = "spafiii@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Dotenv dotenv = Dotenv.load();

        String GMAIL_APPLICATION_PASSWORD = dotenv.get("GMAIL_APPLICATION_PASSWORD");
        String GMAIL_APPLICATION_EMAIL = dotenv.get("GMAIL_APPLICATION_EMAIL");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(GMAIL_APPLICATION_EMAIL, GMAIL_APPLICATION_PASSWORD);

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Welcome to Next Surveys!");

            // Now set the actual message (used for text mail)
//            message.setText("This is actual message");

//            Send HTML mail
            message.setContent(email, "text/html");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}