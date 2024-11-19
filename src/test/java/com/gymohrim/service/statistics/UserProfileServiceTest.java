package com.gymohrim.service.statistics;


import com.gymohrim.entity.User;
import com.gymohrim.entity.UserProfile;
import com.gymohrim.exception.user.UserNotFoundException;
import com.gymohrim.exception.user.UserProfileNotFoundException;
import com.gymohrim.exception.user.UserProfileSaveException;
import com.gymohrim.repository.UserProfileRepository;
import com.gymohrim.repository.UserRepository;
import com.gymohrim.service.user.UserProfileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileService userProfileService;


    @Test
    @DisplayName("Test findByEmail functionality")
    void testFindByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userProfileService.findByEmail(email);

        assertEquals(user, result);
    }

    @Test
    @DisplayName("Test findByEmail with non-existent email")
    void testFindByEmailWithNonExistentEmail() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userProfileService.findByEmail(email);
        });
    }

    @Test
    @DisplayName("Test getUserProfile functionality")
    void testGetUserProfile() {
        User user = new User();
        user.setEmail("test@example.com");
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);

        when(userProfileRepository.findByUser(user)).thenReturn(Optional.of(userProfile));

        Optional<UserProfile> result = userProfileService.getUserProfile(user);

        assertTrue(result.isPresent());
        assertEquals(userProfile, result.get());
    }

    @Test
    @DisplayName("Test getUserProfile with no profile found")
    void testGetUserProfileWithNoProfile() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userProfileRepository.findByUser(user)).thenReturn(Optional.empty());

        Optional<UserProfile> result = userProfileService.getUserProfile(user);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Test saveOrUpdateUserProfile for new profile")
    void testSaveOrUpdateUserProfileForNewProfile() {
        User user = new User();
        user.setEmail("test@example.com");
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);

        userProfileService.saveOrUpdateUserProfile(userProfile);

        verify(userProfileRepository, times(1)).save(userProfile);
    }

    @Test
    @DisplayName("Test saveOrUpdateUserProfile for existing profile")
    void testSaveOrUpdateUserProfileForExistingProfile() {
        User user = new User();
        user.setEmail("test@example.com");
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setId(1);

        when(userProfileRepository.existsById(userProfile.getId())).thenReturn(true);

        userProfileService.saveOrUpdateUserProfile(userProfile);

        verify(userProfileRepository, times(1)).save(userProfile);
    }

    @Test
    @DisplayName("Test saveOrUpdateUserProfile when saving existing profile with updates")
    void testSaveOrUpdateUserProfileWithUpdates() {
        User user = new User();
        user.setEmail("test@example.com");
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);

        when(userProfileRepository.findByUser(user)).thenReturn(Optional.of(new UserProfile()));

        userProfileService.saveOrUpdateUserProfile(userProfile);

        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    @DisplayName("Test saveOrUpdateUserProfile throws UserProfileSaveException on error")
    void testSaveOrUpdateUserProfileThrowsException() {
        User user = new User();
        user.setEmail("test@example.com");
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);

        doThrow(new RuntimeException("Database error")).when(userProfileRepository).save(userProfile);

        assertThrows(UserProfileSaveException.class, () -> {
            userProfileService.saveOrUpdateUserProfile(userProfile);
        });
    }

    @Test
    @DisplayName("Test deleteUserProfile functionality")
    void testDeleteUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1);

        userProfileService.deleteUserProfile(userProfile);

        verify(userProfileRepository, times(1)).delete(userProfile);
    }

    @Test
    @DisplayName("Test deleteUserProfile throws UserProfileNotFoundException")
    void testDeleteUserProfileThrowsException() {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1);

        doThrow(new RuntimeException("Database error")).when(userProfileRepository).delete(userProfile);

        assertThrows(UserProfileNotFoundException.class, () -> {
            userProfileService.deleteUserProfile(userProfile);
        });
    }
}
