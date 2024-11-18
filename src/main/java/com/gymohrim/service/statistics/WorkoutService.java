package com.gymohrim.service.statistics;


import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.Workout;
import com.gymohrim.exception.statistics.DailyRecordNotFoundException;
import com.gymohrim.exception.statistics.WorkoutNotFoundException;
import com.gymohrim.repository.DailyRecordRepository;
import com.gymohrim.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final DailyRecordRepository dailyRecordRepository;



    @Transactional
    public Workout saveOrUpdateWorkout(Workout workout) {
        Optional<Workout> existingWorkout = workoutRepository.findByDailyRecord(workout.getDailyRecord());

        if (existingWorkout.isPresent()) {
            Workout existing = existingWorkout.get();
            existing.setWorkoutType(workout.getWorkoutType());
            existing.setNotes(workout.getNotes());
            existing.setStartTime(workout.getStartTime());
            existing.setEndTime(workout.getEndTime());
            existing.setDuration(workout.getDuration());
            workoutRepository.save(existing);
            log.info("Updated Workout ID: {}", existing.getId());
            return existing;
        } else {
            workoutRepository.save(workout);
            log.info("Saved new Workout ID: {}", workout.getId());
            return workout;
        }
    }


    public Workout startWorkout(Integer dailyRecordId) {
        DailyRecord dailyRecord = dailyRecordRepository.findById(dailyRecordId)
                .orElseThrow(() -> new DailyRecordNotFoundException("DailyRecord with id " + dailyRecordId + " not found"));

        Optional<Workout> existingWorkout = workoutRepository.findByDailyRecord(dailyRecord);

        Workout workout;
        if (existingWorkout.isPresent()) {
            workout = existingWorkout.get();
            workout.setStartTime(LocalDateTime.now());
        } else {
            workout = new Workout();
            workout.setDailyRecord(dailyRecord);
            workout.setStartTime(LocalDateTime.now());
        }

        workoutRepository.save(workout);
        log.info("Started Workout ID: {} for DailyRecord ID: {}", workout.getId(), dailyRecordId);
        return workout;
    }


    public Workout endWorkout(Integer workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException("Workout with id " + workoutId + " not found"));

        workout.setEndTime(LocalDateTime.now());
        workout.setDuration(Duration.between(workout.getStartTime(), workout.getEndTime()).getSeconds());
        workoutRepository.save(workout);

        log.info("Ended Workout ID: {} with Duration: {} seconds", workout.getId(), workout.getDuration());
        return workout;
    }

    public Workout findById(Integer id) {
        log.info("Finding Workout with ID: {}", id);
        return workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException("Workout with id " + id + " not found"));
    }






}
