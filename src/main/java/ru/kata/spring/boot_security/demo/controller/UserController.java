package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;



@Controller
@RequestMapping("/user")
public class UserController {

    private UserDetailsServiceImpl userDetailsServiceImpl;
    private UserService userServiceImpl;


    @Autowired
    public UserController(UserService userServiceImpl,UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }


    @GetMapping()
    public String getUserInfo(@AuthenticationPrincipal UserDetails userDetails,
                              Model model){
        String username = userDetails.getUsername();
        User user = userServiceImpl.findByUsername(username);
        model.addAttribute("user", user);
        return "user";
    }
}
