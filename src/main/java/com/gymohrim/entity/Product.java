package com.gymohrim.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String barcode; 

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Nutrition nutrition;
}
