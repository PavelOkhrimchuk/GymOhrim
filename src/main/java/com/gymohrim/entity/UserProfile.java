package com.gymohrim.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_profile")

public class UserProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @DecimalMin("0.0")
    private Double weight;

    @DecimalMin("0.0")
    private Double height;

    @Size(max = 50)
    private String gender;

    //@URL
    @Column(name = "profile_picture_url", length = 1000)
    private String profilePictureUrl;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
