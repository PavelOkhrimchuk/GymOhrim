package com.gymohrim.service.statistics;


import com.gymohrim.entity.Workout;
import com.gymohrim.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public void saveOrUpdateWorkout(Workout workout) {
        Optional<Workout> existingWorkout = workoutRepository.findByDailyRecord(workout.getDailyRecord());

        if (existingWorkout.isPresent()) {

            Workout existing = existingWorkout.get();
            existing.setWorkoutType(workout.getWorkoutType());
            existing.setNotes(workout.getNotes());
            workoutRepository.save(existing);
        } else {

            workoutRepository.save(workout);
        }
    }

    public Workout findById(Integer id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workout with id " + id + " not found"));
    }



}
