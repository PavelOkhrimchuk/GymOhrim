package com.gymohrim.repository;

import com.gymohrim.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkoutRepository extends JpaRepository<Workout,Integer> {
}
