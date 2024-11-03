package itis.spring.controllers;

import itis.spring.models.Role;
import itis.spring.models.User;
import itis.spring.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
public class AdminController {
    private final UserService userService;
    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("users", userService.list());
        return "admin";
    }

    @PostMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        if (user != null && user.getRoles().contains(Role.ROLE_SUPER_ADMIN)) {
            return "redirect:/admin";
        }
        userService.banUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model) {
        if (user != null && user.getRoles().contains(Role.ROLE_SUPER_ADMIN)) {
            return "redirect:/admin";
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PostMapping("/admin/user/edit")
    public String userEdit(@RequestParam("userId") User user, @RequestParam("selectedRole") String selectedRole, Principal principal, Model  model) {
        userService.changeUserRoles(user, selectedRole, userService.getUserByPrincipal(principal));
        return "redirect:/admin";
    }
}