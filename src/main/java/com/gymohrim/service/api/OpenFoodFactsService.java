package com.gymohrim.service.api;

import com.gymohrim.dto.openfoodfacts.api.ProductDetailsDto;
import com.gymohrim.dto.openfoodfacts.api.ProductResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenFoodFactsService {

    private final RestTemplate restTemplate;

    public OpenFoodFactsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductDetailsDto getProductInfo(String barcode) {
        String url = "https://world.openfoodfacts.net/api/v2/product/" + barcode;
        ProductResponseDto response = restTemplate.getForObject(url, ProductResponseDto.class);
        return response != null ? response.getProduct() : null;
    }
}
