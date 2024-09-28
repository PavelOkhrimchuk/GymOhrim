package com.gymohrim.repository;

import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Integer> {
    Optional<Nutrition> findByDailyRecord(DailyRecord dailyRecord);
}
