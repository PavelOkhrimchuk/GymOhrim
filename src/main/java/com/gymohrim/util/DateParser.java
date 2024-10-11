package com.gymohrim.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public static Date parseDate(String dateStr) throws ParseException {
        return dateFormat.parse(dateStr);
    }
}
