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

class MotorbikeTest {
    Motorbike motorbike;

    /**
     * Set up the environment
     * Create a new Motorbike in attributes
     */
    @BeforeEach
    void setUp() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);
            motorbike = new Motorbike("Yamaha", "MT-07", 2019, 700);
        }
    }

    /**
     * Test the dailyRentalPrice method
     * The dailyRentalPrice method should return 175 for a motorbike with a cylinder capacity of 700
     * result provided by 0.25 * 700
     */
    @Tag("agency")
    @DisplayName("Test the dailyRentalPrice method")
    @Test
    void dailyRentalPrice() {
        // Given
        Double expectedPrice = 0.25 * 700;

        // When
        Double price = motorbike.dailyRentalPrice();

        // Then
        assertThat(price)
                .isEqualTo(expectedPrice);
    }

    /**
     * Test the toString method
     */
    @Tag("agency")
    @DisplayName("Test the toString method")
    @Test
    void testToString() {
        // Given
        String expected = "Motorbike, Yamaha, MT-07, 2019, 700cm3 :175.0€/day";

        // When
        String result = motorbike.toString();

        // Then
        assertThat(result)
                .isEqualTo(expected);
    }

    /**
     * Test the constructor with invalid cylinder capacity
     */
    @Tag("agency")
    @DisplayName("Test the constructor with invalid cylinder capacity")
    @Test
    void testConstructorWithInvalidCylinderCapacity() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);
            assertThatThrownBy(() -> new Motorbike("Yamaha", "MT-07", 2019, 49))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("La cylindrée doit être supérieure à 50, votre cylindrée est égale à : 49");
        }
    }
}