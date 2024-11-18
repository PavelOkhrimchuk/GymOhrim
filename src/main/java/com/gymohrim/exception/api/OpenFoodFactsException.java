package com.gymohrim.exception.api;

public class OpenFoodFactsException extends RuntimeException {

    public OpenFoodFactsException(String message) {
        super(message);
    }

    public OpenFoodFactsException(String message, Throwable cause) {
        super(message, cause);
    }
}
