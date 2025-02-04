package com.rentalagency.util;

import java.time.Year;

public class TimeProvider {
    /**
     * Get the current year
     * @return the current year
     */
    public static int currentYearValue() {
        return Year.now().getValue();
    }
}
