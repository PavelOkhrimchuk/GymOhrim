package com.gymohrim.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "workout")
public class Workout {


    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Temporal(TemporalType.DATE)
    private Date date;


    private String workoutType;

    @Column(columnDefinition = "TEXT")
    private String notes;

}
