package com.gymohrim.service.statistics;


import com.gymohrim.entity.WorkoutExercise;
import com.gymohrim.repository.WorkoutExerciseRepository;
import com.gymohrim.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;




    public void saveOrUpdateWorkoutExercise(WorkoutExercise workoutExercise) {

        Optional<WorkoutExercise> existingWorkoutExercise = workoutExerciseRepository.findByWorkout(workoutExercise.getWorkout());

        if (existingWorkoutExercise.isPresent()) {
            WorkoutExercise existing = existingWorkoutExercise.get();

            existing.setExercise(workoutExercise.getExercise());
            existing.setReps(workoutExercise.getReps());
            existing.setSets(workoutExercise.getSets());
            existing.setWeight(workoutExercise.getWeight());
            workoutExerciseRepository.save(existing);
        } else {
            workoutExerciseRepository.save(workoutExercise);
        }


    }


}
