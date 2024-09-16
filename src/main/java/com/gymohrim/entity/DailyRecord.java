package com.gymohrim.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "daily_record")
public class DailyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToOne(mappedBy = "dailyRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private Workout workout;

    @OneToOne(mappedBy = "dailyRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private Nutrition nutrition;


}
