package com.gymohrim.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @Column(unique = true, nullable = false)
    private String email;



    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;
}
