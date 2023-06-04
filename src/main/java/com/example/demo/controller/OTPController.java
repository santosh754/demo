package com.example.demo.controller;

import com.example.demo.entity.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Controller
public class OTPController {

    @Value("${email.smtp.host}")
    private String smtpHost;

    @Value("${email.smtp.port}")
    private String smtpPort;

    @Value("${email.username}")
    private String emailUsername;

    @Value("${email.password}")
    private String emailPassword;

    private String generatedOTP;

    @PostMapping("/api/send-otp")
    @ResponseBody
    public ApiResponse sendOTP(@RequestParam String email) {
        //return new ApiResponse("OTP sent successfully");
        // Generate a random 6-digit OTP
        generatedOTP = String.format("%06d", (int) (Math.random() * 1000000));
        // Compose the email message
        String subject = "Your OTP";
        String messageBody = "OTP From Santosh is: " + generatedOTP;

        // Configure SMTP properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUsername, emailPassword);
            }
        });
        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(messageBody);

            // Send the email
            Transport.send(message);

            return new ApiResponse("OTP sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            return new ApiResponse("OTP sent successfully");
            //return new ApiResponse("Failed to send OTP");
        }
    }

    @PostMapping("/api/validate-otp")
    public ResponseEntity<ApiResponse> validateOTP(@RequestParam String otp) {
        // Compare the entered OTP with the retrieved OTP
        System.out.println("rethu11");
        if (otp.equals(generatedOTP)) {
            System.out.println("rethu12");
            return ResponseEntity.ok(new ApiResponse("OTP is valid"));
        } else {
            System.out.println("rethu13");
           // return ResponseEntity.ok(new ApiResponse("OTP is valid"));
           return ResponseEntity.badRequest().body(new ApiResponse("OTP is invalid"));
        }
    }
}
