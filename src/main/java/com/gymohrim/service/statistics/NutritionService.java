package com.gymohrim.service.statistics;


import com.gymohrim.dto.openfoodfacts.api.ProductDetailsDto;
import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.Nutrition;
import com.gymohrim.entity.Product;
import com.gymohrim.repository.NutritionRepository;
import com.gymohrim.repository.ProductRepository;
import com.gymohrim.util.RoundingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NutritionService {

    private final NutritionRepository nutritionRepository;

    private final ProductRepository productRepository;





    @Transactional
    public void addNutrition(DailyRecord dailyRecord, String barcode, ProductDetailsDto productDetailsDto, Double grams) {
        Product product = productRepository.findByBarcode(barcode);

        Nutrition nutrition = new Nutrition();
        nutrition.setDailyRecord(dailyRecord);

        nutrition.setCalories((int) ((productDetailsDto.getNutrimentsDto().getEnergyKcal() / 100) * grams));
        nutrition.setProtein(RoundingUtil.roundToOneDecimal(productDetailsDto.getNutrimentsDto().getProteins() / 100 * grams));
        nutrition.setFat(RoundingUtil.roundToOneDecimal(productDetailsDto.getNutrimentsDto().getFat() / 100 * grams));
        nutrition.setCarbohydrates(RoundingUtil.roundToOneDecimal(productDetailsDto.getNutrimentsDto().getCarbohydrates() / 100 * grams));
        nutrition.setProduct(product);
        nutrition.setGrams(grams);

        dailyRecord.getNutritionList().add(nutrition);


        nutritionRepository.save(nutrition);
        log.info("Added nutrition for product: {}, grams: {}", product.getName(), grams);
    }

    public List<Product> searchProducts(String query) {
        if (query == null || query.isEmpty()) {
            log.warn("Search query is null or empty");
            return List.of();
        }

        return productRepository.findByFullTextSearch(query);
    }

    @Transactional
    public void deleteNutrition(Integer id) {
        log.info("Deleting nutrition record with ID: {}", id);
        nutritionRepository.deleteById(id);
    }

    public Optional<Nutrition> findById(Integer id) {
        log.info("Finding nutrition record with ID: {}", id);
        return nutritionRepository.findById(id);
    }




    @Transactional
    public void updateGramsAndRecalculateNutrition(Integer nutritionId, Double newGrams) {
        Optional<Nutrition> nutritionOpt = findById(nutritionId);

        if (nutritionOpt.isPresent()) {
            Nutrition nutrition = nutritionOpt.get();
            double originalGrams = nutrition.getGrams();

            if (originalGrams > 0) {
                double factor = newGrams / originalGrams;

                nutrition.setCalories((int) (nutrition.getCalories() * factor));
                nutrition.setProtein(RoundingUtil.roundToOneDecimal(nutrition.getProtein() * factor));
                nutrition.setFat(RoundingUtil.roundToOneDecimal(nutrition.getFat() * factor));
                nutrition.setCarbohydrates(RoundingUtil.roundToOneDecimal(nutrition.getCarbohydrates() * factor));
                nutrition.setGrams(newGrams);

                nutritionRepository.save(nutrition);
                log.info("Updated nutrition record ID: {}, new grams: {}", nutritionId, newGrams);
            } else {
                log.error("Original grams cannot be zero or negative for nutrition ID: {}", nutritionId);
                throw new IllegalArgumentException("Original grams cannot be zero or negative.");
            }
        } else {
            log.error("Nutrition record not found for ID: {}", nutritionId);
            throw new IllegalArgumentException("Nutrition record not found.");
        }
    }


    @Transactional
    public Map<String, Double> calculateNutritionTotals(List<Nutrition> nutritionList) {
        double totalCalories = nutritionList.stream().mapToDouble(Nutrition::getCalories).sum();
        double totalProtein = nutritionList.stream().mapToDouble(Nutrition::getProtein).sum();
        double totalFat = nutritionList.stream().mapToDouble(Nutrition::getFat).sum();
        double totalCarbohydrates = nutritionList.stream().mapToDouble(Nutrition::getCarbohydrates).sum();

        Map<String, Double> totals = new HashMap<>();
        totals.put("calories", totalCalories);
        totals.put("protein", totalProtein);
        totals.put("fat", totalFat);
        totals.put("carbohydrates", totalCarbohydrates);


        log.info("Calculated nutrition totals: {}", totals);
        log.info("Fetching all products");
        return totals;
    }




    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


}
