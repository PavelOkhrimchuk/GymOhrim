package com.gymohrim.dto.openfoodfacts.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NutrimentsDto {

    @JsonProperty("proteins")
    private double proteins;

    @JsonProperty("fat")
    private double fat;

    @JsonProperty("carbohydrates")
    private double carbohydrates;

    @JsonProperty("energy-kcal")
    private double energyKcal;
}
