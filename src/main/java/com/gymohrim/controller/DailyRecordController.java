package com.gymohrim.controller;


import com.gymohrim.dto.openfoodfacts.api.ProductDetailsDto;
import com.gymohrim.entity.*;
import com.gymohrim.service.api.OpenFoodFactsService;
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
    private final WorkoutService workoutService;
    private final NutritionService nutritionService;
    private final OpenFoodFactsService openFoodFactsService;


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

    @PostMapping("/save-workout")
    public String saveWorkout(@ModelAttribute Workout workout, @RequestParam("dailyRecordId") Integer dailyRecordId) {
        DailyRecord dailyRecord = dailyRecordService.findById(dailyRecordId);
        workout.setDailyRecord(dailyRecord);
        workoutService.saveOrUpdateWorkout(workout);
        return "redirect:/daily-record?selectedDate=" + dailyRecord.getDate();
    }


    @PostMapping("/save-nutrition")
    public String saveNutrition(@RequestParam(value = "productSearch", required = false) String productName,
                                @RequestParam("barcode") String barcode,
                                @RequestParam("dailyRecordId") Integer dailyRecordId,
                                @RequestParam(value = "grams", required = false) Double grams,
                                Model model) {
        DailyRecord dailyRecord = dailyRecordService.findById(dailyRecordId);

        if (barcode != null && !barcode.isEmpty()) {
            ProductDetailsDto productDetails = openFoodFactsService.getProductInfo(barcode);
            if (productDetails != null) {
                if (grams != null) {
                    nutritionService.addNutrition(dailyRecord, barcode, productDetails, grams);
                }
            }
        } else if (productName != null && !productName.isEmpty()) {
            List<Product> foundProducts = nutritionService.searchProducts(productName);
            model.addAttribute("dailyRecord", dailyRecord);
            if (!foundProducts.isEmpty()) {
                model.addAttribute("foundProducts", foundProducts);

            } else {
                model.addAttribute("error", "No record found for the selected date.");
            }
            return "product-selection";

        }

        return "redirect:/daily-record?selectedDate=" + dailyRecord.getDate();
    }




    @GetMapping("/search-products")
    @ResponseBody
    public List<Product> searchProducts(@RequestParam("query") String query) {
        return nutritionService.searchProducts(query);
    }

    @PostMapping("/delete-nutrition")
    public String deleteNutrition(@RequestParam("nutritionId") Integer nutritionId,
                                  @RequestParam("dailyRecordId") Integer dailyRecordId) {
        nutritionService.deleteNutrition(nutritionId); // Удаляем запись о питании
        return "redirect:/daily-record?selectedDate=" + dailyRecordService.findById(dailyRecordId).getDate();
    }


    @GetMapping ("/product-details")
    public String showProductDetails(@RequestParam("nutritionId") Integer nutritionId, Model model) {

        Optional<Nutrition> nutrition = nutritionService.findById(nutritionId);

        if(nutrition.isPresent()) {
            Nutrition nutritionDetails = nutrition.get();
            model.addAttribute("nutritionDetails", nutritionDetails);

        } else {
            model.addAttribute("error", "Nutrition record not found.");
        }

        return "product-details";


    }











}














