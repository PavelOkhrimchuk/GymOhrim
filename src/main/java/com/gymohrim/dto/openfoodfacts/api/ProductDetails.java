package com.gymohrim.dto.openfoodfacts.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ProductDetails {

    @JsonProperty("nutriments")
    private Nutriments nutriments;
}
