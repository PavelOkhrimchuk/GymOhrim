package com.gymohrim.controller.authentication;

import com.gymohrim.entity.User;
import com.gymohrim.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String redirectToRegister() {
        return "redirect:/register";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        validateUser(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        return "login";
    }

    private void validateUser(User user, BindingResult bindingResult) {
        if (userRepository.existsByName(user.getName())) {
            bindingResult.rejectValue("name", "error.user", "User with this name already exists.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "User with this email already exists.");
        }

    }



}




