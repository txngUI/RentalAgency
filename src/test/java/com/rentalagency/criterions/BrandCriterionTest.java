package com.rentalagency.criterions;

import com.rentalagency.agency.Car;
import com.rentalagency.util.TimeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class BrandCriterionTest {
    BrandCriterion brandCriterion;

    /**
     * Set the environment before each test
     * Create a new BrandCriterion with the brand "Toyota" in attributes
     */
    @BeforeEach
    void setUp() {
        brandCriterion = new BrandCriterion("Toyota");
    }

    /**
     * Test if a vehicle satisfies the criterion
     * Mock the current year to 2025 for futures uses
     * Given a vehicle with the brand "Toyota"
     * When the vehicle is tested against the criterion
     * Then the result should be true
     */
    @Tag("agency")
    @DisplayName("Test if a vehicle satisfies the brand criterion")
    @Test
    void testSatisfiesBrandCriterion() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            // When
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);

            // Given
            Car car = new Car("Toyota", "Corolla", 2019, 5);

            // When
            boolean result = brandCriterion.test(car);

            // Then
            assertThat(result).isTrue();
        }
    }

    /**
     * Test if a vehicle does not satisfy the criterion
     * Mock the current year to 2025 for futures uses
     * Given a vehicle with the brand "Honda"
     * When the vehicle is tested against the criterion
     * Then the result should be false
     */
    @Tag("agency")
    @DisplayName("Test if a vehicle does not satisfy the brand criterion")
    @Test
    void testDoesntSatisfiesBrandCriterion() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            // When
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);

            // Given
            Car car = new Car("Honda", "Civic", 2019, 5);

            // When
            boolean result = brandCriterion.test(car);

            // Then
            assertThat(result).isFalse();
        }
    }
}