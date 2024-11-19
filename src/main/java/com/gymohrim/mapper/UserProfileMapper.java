package com.gymohrim.mapper;


import com.gymohrim.dto.userprofile.UserProfileResponseDTO;
import com.gymohrim.dto.userprofile.UserProfileUpdateDTO;
import com.gymohrim.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {


    public UserProfileResponseDTO toResponseDTO(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        return UserProfileResponseDTO.builder()
                .id(userProfile.getId())
                .userEmail(userProfile.getUser() != null ? userProfile.getUser().getEmail() : null)
                .weight(userProfile.getWeight())
                .height(userProfile.getHeight())
                .gender(userProfile.getGender())
                .birthDate(userProfile.getBirthDate())
                .profilePictureUrl(userProfile.getProfilePictureUrl())
                .build();
    }


    public UserProfile toEntity(UserProfileUpdateDTO dto, UserProfile existingProfile) {
        if (dto == null) {
            return existingProfile;
        }

        existingProfile.setWeight(dto.getWeight());
        existingProfile.setHeight(dto.getHeight());
        existingProfile.setGender(dto.getGender());
        existingProfile.setBirthDate(dto.getBirthDate());

        return existingProfile;
    }
}
