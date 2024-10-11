package com.gymohrim.service.statistics;


import com.gymohrim.entity.WorkoutExercise;
import com.gymohrim.repository.WorkoutExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;





    public void saveOrUpdateWorkoutExercise(WorkoutExercise workoutExercise) {

        Optional<WorkoutExercise> existingWorkoutExercise = workoutExerciseRepository.findByWorkoutAndExercise(workoutExercise.getWorkout(), workoutExercise.getExercise());

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

    public List<WorkoutExercise> findByWorkoutId(Integer id) {
        return workoutExerciseRepository.findByWorkoutId(id);
    }

    public void deleteWorkoutExercise(Integer id) {
        workoutExerciseRepository.deleteById(id);
    }




}
