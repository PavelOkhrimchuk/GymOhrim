package com.gymohrim.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "workout")
public class Workout {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "daily_record_id", nullable = false)
    private DailyRecord dailyRecord;

    private String workoutType;

    @Column(columnDefinition = "TEXT")
    private String notes;


    private LocalDateTime startTime;
    private LocalDateTime endTime;


    private Long duration;

}
