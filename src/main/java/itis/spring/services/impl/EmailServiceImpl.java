package itis.spring.services.impl;

import itis.spring.services.EmailService;
import itis.spring.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    private UserService userService;

    private final Map<String, String> resetCodes = new HashMap<>();

    @Override
    public void sendResetCode(String email) {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        String resetCode = String.valueOf(code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Code");
        message.setText("Your password reset code is: " + resetCode);
        emailSender.send(message);
        resetCodes.put(email, resetCode);
    }
    @Override
    public boolean verifyResetCode(String email, String resetCode) {
        String storedResetCode = resetCodes.get(email);
        System.out.println(resetCodes.get(email));
        System.out.println(email  + resetCode + " get");
        return storedResetCode != null && storedResetCode.equals(resetCode);
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        userService.updateUserPassword(  userService.getUserByEmail(email), newPassword);
    }

    @Override
    public boolean isEmailExists(String email) {
        return userService.getUserByEmail(email) != null;
    }
}