package com.gymohrim.util;

public class RoundingUtil {

    public static Double roundToOneDecimal(Double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
