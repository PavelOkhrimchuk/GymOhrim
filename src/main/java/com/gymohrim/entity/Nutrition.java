package com.gymohrim.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "nutrition")
public class Nutrition {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "daily_record_id", nullable = false)
    private DailyRecord dailyRecord;

    private Integer calories;
    private Double protein;
    private Double fat;
    private Double carbohydrates;

}
