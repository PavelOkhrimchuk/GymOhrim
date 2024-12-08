package com.gymohrim.dto.userprofile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDTO {
    private Integer id;
    private String userEmail;
    private Double weight;
    private Double height;
    private String gender;
    private Date birthDate;
    private String profilePictureUrl;
}
