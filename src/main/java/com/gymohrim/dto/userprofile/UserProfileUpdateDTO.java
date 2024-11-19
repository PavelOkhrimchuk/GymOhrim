package com.gymohrim.dto.userprofile;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateDTO {

    @DecimalMin(value = "0.0", message = "Weight must be a positive number.")
    private Double weight;

    @DecimalMin(value = "0.0", message = "Height must be a positive number.")
    private Double height;

    @Size(max = 50, message = "Gender should not exceed 50 characters.")
    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
