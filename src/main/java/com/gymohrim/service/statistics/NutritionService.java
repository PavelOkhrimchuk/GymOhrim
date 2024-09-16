package com.gymohrim.service.statistics;


import com.gymohrim.repository.NutritionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NutritionService {

    private final NutritionRepository nutritionRepository;
}
