package com.gymohrim.controller;

import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.Workout;
import com.gymohrim.service.statistics.DailyRecordService;
import com.gymohrim.service.statistics.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workout")
public class WorkoutController {

    private final DailyRecordService dailyRecordService;
    private final WorkoutService workoutService;


    @PostMapping("/start")
    public String startWorkout(@RequestParam("dailyRecordId") Integer dailyRecordId) {
        Workout workout = workoutService.startWorkout(dailyRecordId);
        return "redirect:/daily-record?selectedDate=" + workout.getDailyRecord().getDate();
    }

    @PostMapping("/end")
    public String endWorkout(@RequestParam("workoutId") Integer workoutId) {
        Workout workout = workoutService.endWorkout(workoutId);
        return "redirect:/daily-record?selectedDate=" + workout.getDailyRecord().getDate();
    }

    @PostMapping("/save")
    public String saveWorkout(@ModelAttribute Workout workout, @RequestParam("dailyRecordId") Integer dailyRecordId) {
        DailyRecord dailyRecord = dailyRecordService.findById(dailyRecordId);
        workout.setDailyRecord(dailyRecord);
        workoutService.saveOrUpdateWorkout(workout);
        return "redirect:/daily-record?selectedDate=" + dailyRecord.getDate();
    }





}
