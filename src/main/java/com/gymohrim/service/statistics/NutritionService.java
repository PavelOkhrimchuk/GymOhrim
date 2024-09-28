package com.gymohrim.service.statistics;


import com.gymohrim.entity.Nutrition;
import com.gymohrim.repository.NutritionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NutritionService {

    private final NutritionRepository nutritionRepository;

    public void saveOrUpdateNutrition(Nutrition nutrition) {


        Optional<Nutrition> existingNutrition = nutritionRepository.findByDailyRecord(nutrition.getDailyRecord());
        if(existingNutrition.isPresent()) {
            Nutrition existing = existingNutrition.get();

            existing.setCalories(nutrition.getCalories());
            existing.setProtein(nutrition.getProtein());
            existing.setFat(nutrition.getFat());
            existing.setCarbohydrates(nutrition.getCarbohydrates());
            nutritionRepository.save(existing);
        }
        else {
            nutritionRepository.save(nutrition);
        }
    }
}
