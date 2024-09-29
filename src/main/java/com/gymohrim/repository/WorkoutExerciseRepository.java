package com.gymohrim.repository;

import com.gymohrim.entity.Exercise;
import com.gymohrim.entity.Workout;
import com.gymohrim.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Integer> {

    List<WorkoutExercise> findByWorkoutId(Integer workoutId);

    Optional<WorkoutExercise> findByWorkoutAndExercise(Workout workout, Exercise exercise);
}
