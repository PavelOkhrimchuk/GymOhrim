package com.gymohrim.service.api;

import com.gymohrim.dto.openfoodfacts.api.ProductDetailsDto;
import com.gymohrim.dto.openfoodfacts.api.ProductResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OpenFoodFactsService {

    private final RestTemplate restTemplate;

    public OpenFoodFactsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductDetailsDto getProductInfo(String barcode) {
        log.info("Fetching product information for barcode: {}", barcode);
        String url = "https://world.openfoodfacts.net/api/v2/product/" + barcode;
        ProductResponseDto response = restTemplate.getForObject(url, ProductResponseDto.class);
        return response != null ? response.getProduct() : null;
    }
}
