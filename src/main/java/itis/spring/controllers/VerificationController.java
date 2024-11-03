package itis.spring.controllers;

import itis.spring.models.User;
import itis.spring.services.EmailService;
import itis.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class VerificationController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @PostMapping("/verify")
    public String verifyCode(@RequestParam("email") String email, @RequestParam("code") String code, HttpSession session, Model model) {
        if (emailService.verifyResetCode(email, code)) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                userService.creatUser(user);
                return "redirect:/login";
            } else {
                model.addAttribute("message", "Ошибка: данные пользователя не найдены в сессии.");
                return "verify";
            }
        } else {
            model.addAttribute("message", "Неверный код подтверждения. Попробуйте еще раз.");
            return "verify";
        }
    }
}

