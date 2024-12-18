package com.gymohrim.controller.authentication;

import com.gymohrim.dto.user.UserRegistrationDTO;
import com.gymohrim.entity.User;
import com.gymohrim.mapper.UserMapper;
import com.gymohrim.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @GetMapping("/")
    public String redirectToRegister() {
        log.info("Redirecting to registration page.");
        return "redirect:/register";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        log.info("Displaying registration form.");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid UserRegistrationDTO userDto, BindingResult bindingResult) {
        log.info("Attempting to register user: {}", userDto.getName());
        validateUser(userDto, bindingResult);

        if (bindingResult.hasErrors()) {
            log.warn("Registration failed due to validation errors for user: {}", userDto.getName());
            return "register";
        }

        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        log.info("User registered successfully: {}", user.getName());
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
            log.warn("Login attempt failed due to invalid credentials.");
        }
        log.info("Displaying login form.");
        return "login";
    }

    private void validateUser(UserRegistrationDTO userDto, BindingResult bindingResult) {
        if (userRepository.existsByName(userDto.getName())) {
            bindingResult.rejectValue("name", "error.user", "User with this name already exists.");
            log.warn("User with name '{}' already exists.", userDto.getName());
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "User with this email already exists.");
            log.warn("User with email '{}' already exists.", userDto.getEmail());
        }
    }



}




