package com.gymohrim.service.api;

import com.gymohrim.dto.openfoodfacts.api.ProductDetailsDto;
import com.gymohrim.dto.openfoodfacts.api.ProductResponseDto;
import com.gymohrim.exception.api.OpenFoodFactsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
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

        try {
            ProductResponseDto response = restTemplate.getForObject(url, ProductResponseDto.class);
            if (response == null || response.getProduct() == null) {
                log.error("Product not found for barcode: {}", barcode);
                throw new OpenFoodFactsException("Product not found for barcode: " + barcode);
            }
            return response.getProduct();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error fetching product information for barcode {}: {}", barcode, e.getMessage());
            throw new OpenFoodFactsException("Error fetching product information from OpenFoodFacts API", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching product information for barcode {}: {}", barcode, e.getMessage());
            throw new OpenFoodFactsException("Unexpected error occurred while fetching product information", e);
        }
    }
}
