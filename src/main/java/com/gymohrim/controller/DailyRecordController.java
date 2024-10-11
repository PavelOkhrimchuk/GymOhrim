package com.gymohrim.controller;


import com.gymohrim.entity.*;
import com.gymohrim.service.statistics.DailyRecordService;
import com.gymohrim.service.statistics.NutritionService;
import com.gymohrim.service.user.UserProfileService;
import com.gymohrim.util.DateParser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/daily-record")
public class DailyRecordController {

    private final UserProfileService userProfileService;
    private final DailyRecordService dailyRecordService;
    private final NutritionService nutritionService;

    @GetMapping
    public String showDailyRecord(@RequestParam("selectedDate") String selectedDate,
                                  @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userProfileService.findByEmail(userDetails.getUsername());

        Date date;

        try {
            date = DateParser.parseDate(selectedDate);
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

            List<Nutrition> nutritionList = dailyRecord.getNutritionList();
            model.addAttribute("nutritionList", nutritionList != null ? nutritionList : new ArrayList<>());


            Map<String, Double> totals = nutritionService.calculateNutritionTotals(nutritionList);

            model.addAttribute("totalCalories", totals.get("calories"));
            model.addAttribute("totalProtein", totals.get("protein"));
            model.addAttribute("totalFat", totals.get("fat"));

            model.addAttribute("totalCarbohydrates", totals.get("carbohydrates"));
        } else {
            model.addAttribute("error", "No record found for the selected date.");
        }

        List<Product> products = nutritionService.getAllProducts();
        model.addAttribute("products", products);

        return "daily-record";
    }









}














