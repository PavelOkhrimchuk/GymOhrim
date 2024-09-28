package com.gymohrim.controller;


import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.Nutrition;
import com.gymohrim.entity.User;
import com.gymohrim.entity.Workout;
import com.gymohrim.service.statistics.DailyRecordService;
import com.gymohrim.service.statistics.NutritionService;
import com.gymohrim.service.statistics.WorkoutService;
import com.gymohrim.service.user.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/daily-record")
public class DailyRecordController {

    private final UserProfileService userProfileService;
    private final DailyRecordService dailyRecordService;
    private final WorkoutService workoutService;
    private final NutritionService nutritionService;

    @GetMapping
    public String showDailyRecord(@RequestParam("selectedDate") String selectedDate,
                                  @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userProfileService.findByEmail(userDetails.getUsername());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(selectedDate);
        } catch (ParseException e) {
            model.addAttribute("error", "Invalid date format");
            return "daily-record";
        }

        Optional<DailyRecord> dailyRecordOpt = dailyRecordService.findByDateAndUser(date, user);

        if (dailyRecordOpt.isPresent()) {
            DailyRecord dailyRecord = dailyRecordOpt.get();
            model.addAttribute("dailyRecord", dailyRecord);

            Workout workout = dailyRecord.getWorkout();
            model.addAttribute("workout", workout != null ? workout : new Workout());
            Nutrition nutrition = dailyRecord.getNutrition();
            model.addAttribute("nutrition", nutrition != null ? nutrition : new Nutrition());
        } else {
            model.addAttribute("error", "No record found for the selected date.");
        }

        return "daily-record";
    }


    @PostMapping("/save-workout")
    public String saveWorkout(@ModelAttribute Workout workout, @RequestParam("dailyRecordId") Integer dailyRecordId) {
        DailyRecord dailyRecord = dailyRecordService.findById(dailyRecordId);
        workout.setDailyRecord(dailyRecord);
        workoutService.saveOrUpdateWorkout(workout);
        return "redirect:/daily-record?selectedDate=" + dailyRecord.getDate();
    }

    @PostMapping("/save-nutrition")
    public String saveNutrition(@ModelAttribute Nutrition nutrition, @RequestParam("dailyRecordId") Integer dailyRecordId) {
        DailyRecord dailyRecord = dailyRecordService.findById(dailyRecordId);
        nutrition.setDailyRecord(dailyRecord);
        nutritionService.saveOrUpdateNutrition(nutrition);
        return "redirect:/daily-record?selectedDate=" + dailyRecord.getDate();
    }





}

