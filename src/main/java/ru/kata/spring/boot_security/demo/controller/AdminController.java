package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
public class AdminController {

    private UserService userService;
    private RoleService roleService;


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/all")
    public ResponseEntity<Iterable<User>> showAllUsers() {
        return new ResponseEntity<>(userService.showUsers(), HttpStatus.OK);
    }

    /*@GetMapping("/admin/userinfo") //для вывода юзера вверху экрана
    public ResponseEntity<User> showUserInfo() {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userService.findByUsername(details.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

     */



    @GetMapping("/admin/showById/{id}")
    public ResponseEntity<User> showUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.showById(id), HttpStatus.OK);
    }

    @PostMapping("/admin/admin/save")
    public ResponseEntity<User> newUser(@RequestBody User newUser) {
        userService.saveUser(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PutMapping("/admin/update/{id}")
    public ResponseEntity<User> editUser(@RequestBody User editUser) {
        userService.saveUser(editUser);
        return new ResponseEntity<>(editUser, HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



   /* @GetMapping()
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



    @PostMapping(value="/save")
    public String create(@ModelAttribute User newUser) {
        List<Role> roles = new ArrayList<>();
        for (Role role: newUser.getRoles()) {
            roles.add(roleService.getRoleByRoleName(role.getRoleName()));
        }
        newUser.setRoles(roles);

        userService.saveUser(newUser);
        return "redirect:/admin";
    }


    //@PatchMapping(value="/update/{id}")
    //public String update(@ModelAttribute("user") User user, ModelMap model, @PathVariable("id") Long id) {
        //model.addAttribute("roles", roleService.getDemandedRoles());
        //return "/admin";
    //}


    @PatchMapping(value="/update/{id}")
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

    */

}