package com.gymohrim.mapper;


import com.gymohrim.dto.user.UserRegistrationDTO;
import com.gymohrim.dto.user.UserResponseDTO;
import com.gymohrim.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .authProviderType(user.getAuthProviderType())
                .build();
    }

    //
    public User toEntity(UserRegistrationDTO dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
    }
}