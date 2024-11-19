package com.gymohrim.service.statistics;


import com.gymohrim.entity.WorkoutExercise;
import com.gymohrim.exception.statistics.WorkoutExerciseDeleteException;
import com.gymohrim.repository.WorkoutExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;




    @Transactional
    public void saveExercise(WorkoutExercise workoutExercise) {
            workoutExerciseRepository.save(workoutExercise);

    }

    public List<WorkoutExercise> findByWorkoutId(Integer id) {
        log.info("Finding WorkoutExercises for Workout ID: {}", id);
        return workoutExerciseRepository.findByWorkoutId(id);
    }

    @Transactional
    public void deleteWorkoutExercise(Integer id) {
        if (!workoutExerciseRepository.existsById(id)) {
            throw new WorkoutExerciseDeleteException("WorkoutExercise with ID " + id + " not found, cannot delete.");
        }
        workoutExerciseRepository.deleteById(id);
        log.info("Deleted WorkoutExercise ID: {}", id);
    }




}
