package itis.spring.controllers;

import itis.spring.models.User;
import itis.spring.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j

public class SettingsController {
    public final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/settings")
    public String settings(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "settings";
    }

    @PostMapping("/settings/update")
    public String updateUserSettings(@RequestParam String name,
                                     @RequestParam String numberPhone,
                                     @RequestParam String email,
                                     Principal principal,
                                     Model model) {
        System.out.println(name + numberPhone + email);
        log.info("controller settings update");
        User user = userService.getUserByPrincipal(principal);
        user.setName(name);
        user.setNumberPhone(numberPhone);
        user.setEmail(email);
        userService.updateUser(user);
        model.addAttribute("user", user);
        return "settings";
    }

    @PostMapping("/settings/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword,
                                                 @RequestParam String newPassword,
                                                 @RequestParam String confirmPassword,
                                                 Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("Все поля пароля должны быть заполнены");
        }
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Новый пароль и его подтверждение не совпадают");
        }
        if (newPassword.equals(oldPassword)) {
            return ResponseEntity.badRequest().body("Новый пароль не должен совпадать со старым паролем");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.badRequest().body("Старый пароль указан неверно");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(user);
        return ResponseEntity.ok("Пароль успешно обновлен");
    }

}
