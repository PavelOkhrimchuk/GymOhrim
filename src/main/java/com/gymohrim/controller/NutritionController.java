package com.gymohrim.controller;


import com.gymohrim.dto.openfoodfacts.api.ProductDetailsDto;
import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.Nutrition;
import com.gymohrim.entity.Product;
import com.gymohrim.service.api.OpenFoodFactsService;
import com.gymohrim.service.statistics.DailyRecordService;
import com.gymohrim.service.statistics.NutritionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/nutrition")
public class NutritionController {

    private final NutritionService nutritionService;
    private final OpenFoodFactsService openFoodFactsService;
    private final DailyRecordService dailyRecordService;

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
        nutritionService.deleteNutrition(nutritionId);
        return "redirect:/daily-record?selectedDate=" + dailyRecordService.findById(dailyRecordId).getDate();
    }


    @GetMapping("/product-details")
    public String showProductDetails(@RequestParam("nutritionId") Integer nutritionId,
                                     @RequestParam("selectedDate") String selectedDate,
                                     Model model) {

        Optional<Nutrition> nutrition = nutritionService.findById(nutritionId);

        if(nutrition.isPresent()) {
            Nutrition nutritionDetails = nutrition.get();
            model.addAttribute("nutritionDetails", nutritionDetails);
            model.addAttribute("selectedDate", selectedDate);
        } else {
            model.addAttribute("error", "Nutrition record not found.");
        }

        return "product-details";
    }


    @PostMapping("/update-grams")
    public String updateNutritionGrams(@RequestParam("nutritionId") Integer nutritionId,
                                       @RequestParam("grams") Double grams, Model model) {
        try {
            nutritionService.updateGramsAndRecalculateNutrition(nutritionId, grams);
            Optional<Nutrition> nutritionOpt = nutritionService.findById(nutritionId);
            nutritionOpt.ifPresent(nutrition -> model.addAttribute("nutritionDetails", nutrition));
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Nutrition record not updated");
        }

        return "product-details";
    }
}
