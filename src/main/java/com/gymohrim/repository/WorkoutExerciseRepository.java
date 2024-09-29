package com.gymohrim.repository;

import com.gymohrim.entity.Workout;
import com.gymohrim.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Integer> {
    Optional<WorkoutExercise> findByWorkout(Workout workout);
}
