package com.gymohrim.controller;

import com.gymohrim.dto.userprofile.UserProfileResponseDTO;
import com.gymohrim.dto.userprofile.UserProfileUpdateDTO;
import com.gymohrim.entity.User;
import com.gymohrim.entity.UserProfile;
import com.gymohrim.mapper.UserProfileMapper;
import com.gymohrim.service.FileStorageService;
import com.gymohrim.service.user.UserProfileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserProfileController.class)
@ExtendWith(MockitoExtension.class)

class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    @MockBean
    private FileStorageService fileStorageService;

    @MockBean
    private UserProfileMapper userProfileMapper;

    @MockBean
    private UserDetails userDetails;

    @InjectMocks
    private UserProfileController userProfileController;



    @Test
    @DisplayName("Test showProfile - successful retrieval of user profile")
    @WithMockUser(username = "test@example.com")
    void testShowProfile() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);

        UserProfileResponseDTO profileDTO = new UserProfileResponseDTO();

        when(userProfileService.findByEmail("test@example.com")).thenReturn(user);
        when(userProfileService.getUserProfile(user)).thenReturn(Optional.of(userProfile));
        when(userProfileMapper.toResponseDTO(userProfile)).thenReturn(profileDTO);

        mockMvc.perform(get("/profile")
                        .principal(() -> "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attribute("profile", profileDTO));
    }


    @Test
    @DisplayName("Test updateProfile - successful profile update with file upload")
    @WithMockUser(username = "test@example.com")
    void testUpdateProfile() throws Exception {

        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        UserProfile existingProfile = new UserProfile();
        existingProfile.setUser(user);

        UserProfileUpdateDTO profileDto = UserProfileUpdateDTO.builder()
                .weight(70.5)
                .height(175.0)
                .gender("Male")
                .birthDate(new Date())
                .build();

        MockMultipartFile profilePicture = new MockMultipartFile(
                "profilePicture",
                "profile.jpg",
                "image/jpeg",
                new byte[]{1, 2, 3}
        );

        when(userProfileService.findByEmail("test@example.com")).thenReturn(user);
        when(userProfileService.getUserProfile(user)).thenReturn(Optional.of(existingProfile));
        when(fileStorageService.uploadFile(any(), eq("profile_1"))).thenReturn("http://example.com/profile_1.jpg");
        when(userProfileMapper.toEntity(any(UserProfileUpdateDTO.class), any(UserProfile.class)))
                .thenReturn(existingProfile);


        mockMvc.perform(multipart("/profile")
                        .file(profilePicture)
                        .param("weight", "70.5")
                        .param("height", "175.0")
                        .param("gender", "Male")
                        .param("birthDate", "1990-01-01")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));


        verify(userProfileService, times(1)).findByEmail("test@example.com");
        verify(fileStorageService, times(1)).uploadFile(profilePicture, "profile_1");
        verify(userProfileService, times(1)).saveOrUpdateUserProfile(existingProfile);
    }




}