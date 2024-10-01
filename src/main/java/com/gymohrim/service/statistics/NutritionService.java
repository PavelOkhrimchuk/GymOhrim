package com.gymohrim.service.statistics;


import com.gymohrim.dto.openfoodfacts.api.ProductDetails;
import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.Nutrition;
import com.gymohrim.entity.Product;
import com.gymohrim.repository.NutritionRepository;
import com.gymohrim.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NutritionService {

    private final NutritionRepository nutritionRepository;

    private final ProductRepository productRepository;

    public void addNutrition(DailyRecord dailyRecord, String barcode, ProductDetails productDetails) {
        Product product = productRepository.findByBarcode(barcode);

        Nutrition nutrition = new Nutrition();
        nutrition.setDailyRecord(dailyRecord);
        nutrition.setCalories((int) productDetails.getNutriments().getEnergyKcal());
        nutrition.setProtein(productDetails.getNutriments().getProteins());
        nutrition.setFat(productDetails.getNutriments().getFat());
        nutrition.setCarbohydrates(productDetails.getNutriments().getCarbohydrates());
        nutrition.setProduct(product);

        dailyRecord.getNutritionList().add(nutrition);


        nutritionRepository.save(nutrition);
    }




    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
