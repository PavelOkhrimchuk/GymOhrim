package com.gymohrim.exception.statistics;

public class WorkoutNotFoundException extends RuntimeException{

    public WorkoutNotFoundException(String message) {
        super(message);
    }
}
