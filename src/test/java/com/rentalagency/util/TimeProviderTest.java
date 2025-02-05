package com.rentalagency.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class TimeProviderTest {
    /**
     * Test the currentYearValue method
     * Should return the current year
     */
    @Tag("util")
    @Test
    @DisplayName("Test the currentYearValue method")
    void currentYearValue() {
        // Given
        int currentYear = Year.now().getValue();

        // When
        int result = TimeProvider.currentYearValue();

        // Then
        assertEquals(currentYear, result);
    }
}