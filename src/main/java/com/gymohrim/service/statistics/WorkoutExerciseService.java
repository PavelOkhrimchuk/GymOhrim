package com.gymohrim.service.statistics;


import com.gymohrim.entity.WorkoutExercise;
import com.gymohrim.repository.WorkoutExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;





    public void saveOrUpdateWorkoutExercise(WorkoutExercise workoutExercise) {

        Optional<WorkoutExercise> existingWorkoutWorkoutExercise = workoutExerciseRepository.findByWorkoutAndExercise(workoutExercise.getWorkout(), workoutExercise.getExercise());

        if (existingWorkoutWorkoutExercise.isPresent()) {
            WorkoutExercise existingWorkout = existingWorkoutWorkoutExercise.get();

            existingWorkout.setExercise(workoutExercise.getExercise());
            existingWorkout.setReps(workoutExercise.getReps());
            existingWorkout.setSets(workoutExercise.getSets());
            existingWorkout.setWeight(workoutExercise.getWeight());
            workoutExerciseRepository.save(existingWorkout);

            log.info("Updated WorkoutExercise ID: {}", existingWorkout.getId());
        } else {

            workoutExerciseRepository.save(workoutExercise);
            log.info("Saved new WorkoutExercise ID: {}", workoutExercise.getId());
        }


    }

    public List<WorkoutExercise> findByWorkoutId(Integer id) {
        log.info("Finding WorkoutExercises for Workout ID: {}", id);
        return workoutExerciseRepository.findByWorkoutId(id);
    }

    public void deleteWorkoutExercise(Integer id) {
        workoutExerciseRepository.deleteById(id);
        log.info("Deleted WorkoutExercise ID: {}", id);
    }




}
