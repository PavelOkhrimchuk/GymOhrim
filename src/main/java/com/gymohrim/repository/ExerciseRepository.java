package com.gymohrim.repository;

import com.gymohrim.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    @Query("SELECT DISTINCT e.muscleGroup FROM Exercise e")
    List<String> findDistinctMuscleGroups();

    List<Exercise> findByMuscleGroup(String muscleGroup);
}
