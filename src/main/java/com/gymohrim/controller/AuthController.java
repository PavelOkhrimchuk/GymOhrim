package com.gymohrim.controller;

import com.gymohrim.entity.User;
import com.gymohrim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {



    private final UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email is already registered.");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
