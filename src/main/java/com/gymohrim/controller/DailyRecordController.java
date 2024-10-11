package com.gymohrim.controller;


import com.gymohrim.entity.*;
import com.gymohrim.service.statistics.DailyRecordService;
import com.gymohrim.service.statistics.NutritionService;
import com.gymohrim.service.user.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            List<Nutrition> nutritionList = dailyRecord.getNutritionList();

            model.addAttribute("nutritionList", nutritionList != null ? nutritionList : new ArrayList<>());
            int totalCalories = nutritionList.stream().mapToInt(Nutrition::getCalories).sum();
            double totalProtein = nutritionList.stream().mapToDouble(Nutrition::getProtein).sum();
            double totalFat = nutritionList.stream().mapToDouble(Nutrition::getFat).sum();
            double totalCarbohydrates = nutritionList.stream().mapToDouble(Nutrition::getCarbohydrates).sum();
            model.addAttribute("totalCalories", totalCalories);
            model.addAttribute("totalProtein", totalProtein);
            model.addAttribute("totalFat", totalFat);
            model.addAttribute("totalCarbohydrates", totalCarbohydrates);
        } else {
            model.addAttribute("error", "No record found for the selected date.");
        }

        List<Product> products = nutritionService.getAllProducts();
        model.addAttribute("products", products);

        return "daily-record";
    }








}














