package com.rentalagency.criterions;

import com.rentalagency.agency.Car;
import com.rentalagency.util.TimeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class MaxPriceCriterionTest {
    MaxPriceCriterion maxPriceCriterion;

    /**
     * Set the environment before each test
     */
    @BeforeEach
    void setUp() {
        maxPriceCriterion = new MaxPriceCriterion(100);
    }

    /**
     * Test if the car satisfies the max price criterion
     * Mock the current year to 2025 for futures uses
     * Given a car with the production year 2019
     * When the car is tested against the criterion
     * Then the result should be true
     */
    @Tag("agency")
    @DisplayName("Test if the car satisfies the max price criterion")
    @Test
    void testSatisfiesMaxPriceCriterion() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            // When
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);

            // Given
            Car car = new Car("Toyota", "Corolla", 2019, 4);

            // When
            boolean result = maxPriceCriterion.test(car);

            // Then
            assertTrue(result);
        }
    }

    /**
     * Test if the car does not satisfy the max price criterion
     * Mock the current year to 2025 for futures uses
     * Given a car with the production year 2022
     * When the car is tested against the criterion
     * Then the result should be false
     */
    @Tag("agency")
    @DisplayName("Test if the car does not satisfy the max price criterion")
    @Test
    void testNotSatisfiesMaxPriceCriterion() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            // When
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);

            // Given
            Car car = new Car("Toyota", "Corolla", 2022, 4);

            // When
            boolean result = maxPriceCriterion.test(car);

            // Then
            assertFalse(result);
        }
    }
}