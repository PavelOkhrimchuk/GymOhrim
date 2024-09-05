package com.gymohrim.entity;


import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.*;

import java.util.Date;
import java.util.UUID;

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

    private Date birthDate;
}
