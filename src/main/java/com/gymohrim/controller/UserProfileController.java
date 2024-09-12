package com.gymohrim.controller;


import com.gymohrim.entity.User;
import com.gymohrim.entity.UserProfile;
import com.gymohrim.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userProfileService.findByEmail(userDetails.getUsername());
        UserProfile userProfile = userProfileService.getUserProfile(user)
                .orElse(new UserProfile());
        model.addAttribute("profile", userProfile);
        return "profile";
    }

    @PostMapping
    public String updateProfile(@ModelAttribute("profile") @Valid UserProfile userProfile,
                                @AuthenticationPrincipal UserDetails userDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile";
        }

        User user = userProfileService.findByEmail(userDetails.getUsername());
        userProfile.setUser(user);

        userProfileService.saveOrUpdateUserProfile(userProfile);

        return "redirect:/profile";
    }

    @PostMapping("/delete")
    public String deleteProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userProfileService.findByEmail(userDetails.getUsername());
        userProfileService.getUserProfile(user)
                .ifPresent(userProfileService::deleteUserProfile);
        return "redirect:/profile";
    }
}
