package com.gymohrim.service.statistics;


import com.gymohrim.entity.Exercise;
import com.gymohrim.exception.statistics.ExerciseNotFoundException;
import com.gymohrim.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public List<Exercise> findAllExercises() {
        log.debug("Fetching all exercises");
        List<Exercise> exercises = exerciseRepository.findAll();
        log.debug("Retrieved {} exercises", exercises.size());
        return exercises;
    }

    public List<String> findAllMuscleGroups() {
        log.debug("Fetching distinct muscle groups");
        return exerciseRepository.findDistinctMuscleGroups();
    }

    public List<Exercise> findByMuscleGroup(String muscleGroup) {
        log.debug("Fetching exercises for muscle group: {}", muscleGroup);
        return exerciseRepository.findByMuscleGroup(muscleGroup);
    }

    public Exercise findById(Integer id) {
        log.debug("Fetching exercise with ID: {}", id);
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise with ID " + id + " not found"));
    }
}
