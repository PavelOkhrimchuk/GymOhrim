package com.gymohrim.service.user;


import com.gymohrim.entity.User;
import com.gymohrim.entity.UserProfile;
import com.gymohrim.repository.UserProfileRepository;
import com.gymohrim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private final UserRepository userRepository;


    public User findByEmail(String email) {
        log.info("Attempting to find user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found for email: {}", email);
                    return new UsernameNotFoundException("User not found");
                });
    }

    public Optional<UserProfile> getUserProfile(User user) {
        log.info("Retrieving user profile for user: {}", user.getEmail());
        return userProfileRepository.findByUser(user);
    }

    public void saveOrUpdateUserProfile(UserProfile userProfile) {
        log.info("Saving or updating user profile for user: {}", userProfile.getUser().getEmail());

        if (userProfile.getId() != null && userProfileRepository.existsById(userProfile.getId())) {
            log.debug("Updating existing user profile with ID: {}", userProfile.getId());
            userProfileRepository.save(userProfile);
        } else {
            Optional<UserProfile> existingProfile = userProfileRepository.findByUser(userProfile.getUser());
            if (existingProfile.isPresent()) {
                UserProfile existing = existingProfile.get();
                log.debug("Updating fields for existing profile: {}", existing.getId());
                existing.setWeight(userProfile.getWeight());
                existing.setHeight(userProfile.getHeight());
                existing.setGender(userProfile.getGender());
                existing.setBirthDate(userProfile.getBirthDate());
                existing.setProfilePictureUrl(userProfile.getProfilePictureUrl());
                userProfileRepository.save(existing);
            } else {
                log.debug("Saving new user profile");
                userProfileRepository.save(userProfile);
            }
        }
        log.info("User profile saved/updated successfully for user: {}", userProfile.getUser().getEmail());
    }



    public void deleteUserProfile(UserProfile userProfile) {
        log.info("Deleting user profile with ID: {}", userProfile.getId());
        userProfileRepository.delete(userProfile);
        log.info("User profile deleted successfully for ID: {}", userProfile.getId());
    }



}
