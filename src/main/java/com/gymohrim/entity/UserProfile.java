package com.gymohrim.entity;


import jakarta.persistence.*;
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

    private Double weight;

    private Double height;

    private String gender;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
