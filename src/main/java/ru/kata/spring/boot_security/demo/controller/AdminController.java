package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


   @GetMapping()
    public String users(@AuthenticationPrincipal UserDetails userDetails,
                        Model model){
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("users", userService.showUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getDemandedRoles());
        return "admin";
    }



    @PostMapping("/save")
    public String create(@ModelAttribute("user") User newUser,
                         @RequestParam(name="roles", required = false) String[] roles) {
        List<Role> roles1 = new ArrayList<>();
        for (String role: roles) {
            roles1.add(roleService.getRoleByRoleName(role));
            newUser.setRoles(roles1);
        }
        userService.saveUser(newUser);
        return "redirect:/admin";
    }



    @PatchMapping("/update/{id}")
    public String update
            (@ModelAttribute("user") User user, @PathVariable("id") long id,
             @RequestParam(name="roles", required = false) String[] roles) {
        List<Role> roles1 = new ArrayList<>();
        if(roles == null) {
            user.setRoles((List<Role>) userService.showById(id).getRoles());
        } else {
            for (String role: roles) {
                roles1.add(roleService.getRoleByRoleName(role));
                user.setRoles(roles1);
            }
        }
        userService.update(id, user);
        return "redirect:/admin";
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }



}