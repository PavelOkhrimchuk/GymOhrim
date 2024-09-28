package com.gymohrim.repository;

import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WorkoutRepository extends JpaRepository<Workout,Integer> {
    Optional<Workout> findByDailyRecord(DailyRecord dailyRecord);
}
