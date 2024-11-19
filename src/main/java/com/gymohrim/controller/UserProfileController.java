package com.gymohrim.controller;


import com.gymohrim.dto.userprofile.UserProfileResponseDTO;
import com.gymohrim.dto.userprofile.UserProfileUpdateDTO;
import com.gymohrim.entity.User;
import com.gymohrim.entity.UserProfile;
import com.gymohrim.mapper.UserProfileMapper;
import com.gymohrim.service.FileStorageService;
import com.gymohrim.service.user.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final FileStorageService fileStorageService;
    private final UserProfileMapper  userProfileMapper;



    @GetMapping
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userProfileService.findByEmail(userDetails.getUsername());
        UserProfile userProfile = userProfileService.getUserProfile(user).orElse(new UserProfile());
        UserProfileResponseDTO profileDTO = userProfileMapper.toResponseDTO(userProfile);
        model.addAttribute("profile", profileDTO);
        return "profile";
    }

    @PostMapping
    public String updateProfile(@ModelAttribute("profile") @Valid UserProfileUpdateDTO profileDto,
                                @AuthenticationPrincipal UserDetails userDetails,
                                BindingResult bindingResult,
                                @RequestParam("profilePicture") MultipartFile profilePicture) throws Exception {
        if (bindingResult.hasErrors()) {
            return "profile";
        }

        User user = userProfileService.findByEmail(userDetails.getUsername());
        UserProfile existingProfile = userProfileService.getUserProfile(user)
                .orElse(new UserProfile());
        existingProfile.setUser(user);

        if (!profilePicture.isEmpty()) {
            String fileName = "profile_" + user.getId();
            String fileUrl = fileStorageService.uploadFile(profilePicture, fileName);
            existingProfile.setProfilePictureUrl(fileUrl);
        }

        UserProfile updatedProfile = userProfileMapper.toEntity(profileDto, existingProfile);
        userProfileService.saveOrUpdateUserProfile(updatedProfile);

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
