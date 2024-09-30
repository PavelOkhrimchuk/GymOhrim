package com.gymohrim.service.api;

import com.gymohrim.dto.openfoodfacts.api.ProductDetails;
import com.gymohrim.dto.openfoodfacts.api.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenFoodFactsService {

    private final RestTemplate restTemplate;

    public OpenFoodFactsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductDetails getProductInfo(String barcode) {
        String url = "https://world.openfoodfacts.net/api/v2/product/" + barcode;
        ProductResponse response = restTemplate.getForObject(url, ProductResponse.class);
        return response != null ? response.getProduct() : null;
    }
}
