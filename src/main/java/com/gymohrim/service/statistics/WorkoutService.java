package com.gymohrim.service.statistics;


import com.gymohrim.entity.Workout;
import com.gymohrim.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public void saveOrUpdateWorkout(Workout workout) {
        Optional<Workout> existingWorkout = workoutRepository.findByDailyRecord(workout.getDailyRecord());

        if (existingWorkout.isPresent()) {

            Workout existing = existingWorkout.get();
            existing.setWorkoutType(workout.getWorkoutType());
            existing.setNotes(workout.getNotes());
            workoutRepository.save(existing);
            log.info("Updated Workout ID: {}", existing.getId());
        } else {

            workoutRepository.save(workout);
            log.info("Saved new Workout ID: {}", workout.getId());
        }
    }

    public Workout findById(Integer id) {
        log.info("Finding Workout with ID: {}", id);
        return workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workout with id " + id + " not found"));
    }



}
