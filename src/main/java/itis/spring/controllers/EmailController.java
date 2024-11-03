package itis.spring.controllers;

import itis.spring.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/recovery")
public class EmailController {
    private final EmailService emailService;


    @GetMapping("/forgot-password")
    public String forgotPassword(){
        return "forgot-password";
    }

    @PostMapping("/send-reset-code")
    public String sendResetCode(@RequestParam String email, Model model) {
        if (!emailService.isEmailExists(email)) {
            model.addAttribute("error", "Email does not exist");
            return "forgot-password";
        }
        try {
            emailService.sendResetCode(email);
            model.addAttribute("email", email);
            return "reset-code";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to send reset code");
            return "forgot-password";
        }

    }

    @GetMapping("/reset-code")
    public String resetCode(){
        return "reset-code";
    }


    @PostMapping("/verify-reset-code")
    public String verifyResetCode(@RequestParam String email, @RequestParam String resetCode, Model model) {
        boolean codeVerified = emailService.verifyResetCode(email, resetCode);
        if (codeVerified) {
            model.addAttribute("email", email);
            return "reset-password";
        } else {
            return "access-denied";
        }
    }

    @GetMapping("/reset-password")
    public String resetPassword(){
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        emailService.resetPassword(email, newPassword);
        return "redirect:/login";
    }
}