package com.gymohrim.service.statistics;


import com.gymohrim.entity.Exercise;
import com.gymohrim.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public List<Exercise> findAllExercises() {
        return exerciseRepository.findAll();
    }

    public List<String> findAllMuscleGroups() {
        return exerciseRepository.findDistinctMuscleGroups();
    }


    public List<Exercise> findByMuscleGroup(String muscleGroup) {
        return exerciseRepository.findByMuscleGroup(muscleGroup);
    }


    public Exercise findById(Integer id) {
        return exerciseRepository.findById(id).orElse(null);
    }
}
