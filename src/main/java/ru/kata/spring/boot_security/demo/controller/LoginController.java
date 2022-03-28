package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class LoginController {

    private final UserService userService;
    private final RoleService roleService;


    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String home(Model model, Principal principal) {
        model.addAttribute("users", userService.showUsers());
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("userService", userService);
        model.addAttribute("roleService", roleService);
        return "admin";
    }
}
