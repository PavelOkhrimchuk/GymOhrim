package com.gymohrim.service;


import com.gymohrim.entity.User;
import com.gymohrim.entity.UserProfile;
import com.gymohrim.repository.UserProfileRepository;
import com.gymohrim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public Optional<UserProfile> getUserProfile(User user) {
        return userProfileRepository.findByUser(user);
    }

    public UserProfile saveOrUpdateUserProfile(UserProfile userProfile) {

        if (userProfile.getId() != null && userProfileRepository.existsById(userProfile.getId())) {
            return userProfileRepository.save(userProfile);
        }


        Optional<UserProfile> existingProfile = userProfileRepository.findByUser(userProfile.getUser());
        if (existingProfile.isPresent()) {
            UserProfile existing = existingProfile.get();

            existing.setWeight(userProfile.getWeight());
            existing.setHeight(userProfile.getHeight());
            existing.setGender(userProfile.getGender());
            existing.setBirthDate(userProfile.getBirthDate());
            existing.setProfilePictureUrl(userProfile.getProfilePictureUrl());
            return userProfileRepository.save(existing);
        }

        // В противном случае, создайте новый профиль
        return userProfileRepository.save(userProfile);
    }


    public void deleteUserProfile(UserProfile userProfile) {
        userProfileRepository.delete(userProfile);
    }


}
