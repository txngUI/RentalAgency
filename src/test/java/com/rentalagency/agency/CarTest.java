package com.rentalagency.agency;

import com.rentalagency.util.TimeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class CarTest {
    Car car;

    /**
     * Set up the environment
     * Create a new Car in attributes
     * Mock the current year value to 2025 for futures uses
     */
    @BeforeEach
    void setUp() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);
            car = new Car("Toyota", "Corolla", 2020, 4);
        }
    }

    /**
     * Test the dailyRentalPrice method
     * The dailyRentalPrice method should return 160 for a car with 4 seats
     * result provided by 40 * 4
     * Mock the current year value to 2025 for futures uses
     */
    @Tag("agency")
    @DisplayName("Test the dailyRentalPrice method")
    @Test
    void dailyRentalPrice() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);

            // Given
            Double expectedPrice = 160.0;

            // When
            Double price = car.dailyRentalPrice();

            // Then
            assertThat(price).isEqualTo(expectedPrice);
        }
    }

    /**
     * Test the toString method
     * Check if the toString method returns the correct string
     */
    @Tag("agency")
    @DisplayName("Test the toString method")
    @Test
    void testToString() {
        // Given
        String expected = "Car, Toyota, Corolla, 2020, 4 seats : 160,0€/day";
        // When
        String result = car.toString();

        // Then
        assertThat(result).isEqualTo(expected);
    }

    /**
     * Test the isNew method
     * Check if the car is new (less than 5 years old)
     * Mock the current year value to 2030 to test if the car is not new
     */
    @Tag("agency")
    @DisplayName("Test the isNew method")
    @Test
    void testIsNew() {
        // When
        boolean result = car.isNew();

        // Then
        assertThat(result)
                .isTrue();

        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2030);

            result = car.isNew();

            // Then
            assertThat(result).isFalse();
        }
    }

    /**
     * Test the constructor with invalid number of seats
     * Check if an IllegalArgumentException is thrown when the number of seats is invalid
     */
    @Tag("agency")
    @DisplayName("Test the constructor with invalid number of seats")
    @Test
    void testConstructorWithInvalidNumberOfSeats() {
        assertThatThrownBy(() -> new Car("Toyota", "Corolla", 2020, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le nombre de sièges doit être supérieur à 0, votre nombre de sièges est égal à : 0");
    }
}