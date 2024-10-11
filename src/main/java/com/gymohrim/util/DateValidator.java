package com.gymohrim.util;

import java.util.Date;

public class DateValidator {

    public static void validateDateNotInFuture(Date date) {
        if (date.after(new Date())) {
            throw new IllegalArgumentException("Selected date cannot be in the future.");
        }
    }
}
