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

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;


    @GetMapping
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userProfileService.findByEmail(userDetails.getUsername());

        Optional<UserProfile> userProfile = userProfileService.getUserProfile(user);

        if (userProfile.isPresent()) {
            model.addAttribute("profile", userProfile.get());
        } else {
            model.addAttribute("profile", new UserProfile());
        }
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


        Optional<UserProfile> existingProfile = userProfileService.getUserProfile(user);
        if (existingProfile.isPresent()) {

            UserProfile existing = existingProfile.get();
            existing.setWeight(userProfile.getWeight());
            existing.setHeight(userProfile.getHeight());
            existing.setGender(userProfile.getGender());
            existing.setBirthDate(userProfile.getBirthDate());
            existing.setProfilePictureUrl(userProfile.getProfilePictureUrl());
            userProfileService.saveUserProfile(existing);
        } else {

            userProfileService.saveUserProfile(userProfile);
        }

        return "redirect:/profile";
    }

    @PostMapping("/delete")
    public String deleteProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userProfileService.findByEmail(userDetails.getUsername());

        Optional<UserProfile> userProfile = userProfileService.getUserProfile(user);
        userProfile.ifPresent(userProfileService::deleteUserProfile);
        return "redirect:/profile";
    }
}
