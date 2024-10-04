package com.gymohrim.service.statistics;


import com.gymohrim.dto.openfoodfacts.api.ProductDetailsDto;
import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.Nutrition;
import com.gymohrim.entity.Product;
import com.gymohrim.repository.NutritionRepository;
import com.gymohrim.repository.ProductRepository;
import com.gymohrim.util.RoundingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NutritionService {

    private final NutritionRepository nutritionRepository;

    private final ProductRepository productRepository;

    public void addNutrition(DailyRecord dailyRecord, String barcode, ProductDetailsDto productDetailsDto, Double grams) {
        Product product = productRepository.findByBarcode(barcode);

        Nutrition nutrition = new Nutrition();
        nutrition.setDailyRecord(dailyRecord);

        nutrition.setCalories((int) ((productDetailsDto.getNutrimentsDto().getEnergyKcal() / 100) * grams));
        nutrition.setProtein(RoundingUtil.roundToOneDecimal(productDetailsDto.getNutrimentsDto().getProteins() / 100 * grams));
        nutrition.setFat(RoundingUtil.roundToOneDecimal(productDetailsDto.getNutrimentsDto().getFat() / 100 * grams));
        nutrition.setCarbohydrates(RoundingUtil.roundToOneDecimal(productDetailsDto.getNutrimentsDto().getCarbohydrates() / 100 * grams));
        nutrition.setProduct(product);

        dailyRecord.getNutritionList().add(nutrition);


        nutritionRepository.save(nutrition);
    }




    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
