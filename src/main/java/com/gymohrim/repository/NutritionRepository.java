package com.gymohrim.repository;

import com.gymohrim.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Integer> {

}
