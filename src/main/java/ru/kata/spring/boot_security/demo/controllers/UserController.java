package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/")
public class UserController {

    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/")
    public String helloPage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/login";
    }

    @GetMapping("/sign_in")
    public String signIn(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "sign_in";
    }

    @PostMapping("/sign_in")
    public String saveUser(@ModelAttribute("user") User user) {
        Role roleUser = roleService.getRoles().get(0);
        user.setRoles(List.of(roleUser));
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "user-page";
    }


}
