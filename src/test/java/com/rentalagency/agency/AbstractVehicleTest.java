package com.rentalagency.agency;

import com.rentalagency.util.TimeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractVehicleTest {
    AbstractVehicle vehicle;

    /**
     * Set up the test environment
     * Mock the current year value to 2025 for futures uses
     * Create a vehicle
     */
    @BeforeEach
    void setUp() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);
            vehicle = new Car("Toyota", "Corolla", 2020, 4);
        }

    }

    /**
     * Test the getBrand method
     * Check if the brand of the vehicle is equal to "Toyota"
     */
    @Tag("agency")
    @DisplayName("Test the getBrand method")
    @Test
    void getBrand() {
        // Given
        String brandExpected = "Toyota";

        // Then
        assertThat(vehicle).extracting(AbstractVehicle::getBrand).isEqualTo(brandExpected);
    }

    /**
     * Test the getModel method
     * Check if the model of the vehicle is equal to "Corolla"
     */
    @Tag("agency")
    @DisplayName("Test the getModel method")
    @Test
    void getModel() {
        // Given
        String modelExpected = "Corolla";

        // Then
        assertThat(vehicle).extracting(AbstractVehicle::getModel).isEqualTo(modelExpected);
    }

    /**
     * Test the getProductionYear method
     * Check if the production year of the vehicle is equal to 2020
     */
    @Tag("agency")
    @DisplayName("Test the getProductionYear method")
    @Test
    void getProductionYear() {
        // Given
        int productionYearExpected = 2020;

        // Then
        assertThat(vehicle).extracting(AbstractVehicle::getProductionYear).isEqualTo(productionYearExpected);
    }

    /**
     * Test the equals method
     * Check if the vehicle is equal to itself
     * Check if the vehicle is equal to another vehicle with the same brand, model and production year
     * Check if the vehicle is not equal to another vehicle with the same brand and model but a different production year
     * Check if the vehicle is not equal to another vehicle with the same brand and production year but a different model
     * Check if the vehicle is not equal to another vehicle with the same model and production year but a different brand
     */
    @Tag("agency")
    @DisplayName("Test the equals method")
    @Test
    void testEquals() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);

            // Given
            AbstractVehicle vehicle2 = new Car("Toyota", "Corolla", 2020, 4);
            AbstractVehicle vehicleWithoutSameProductionYear = new Car("Toyota", "Corolla", 2021, 4);
            AbstractVehicle vehicleWithoutSameModel = new Car("Toyota", "Yaris", 2020, 4);
            AbstractVehicle vehicleWithoutSameBrand = new Car("Volkswagen", "Corolla", 2020, 4);

            // Then
            assertThat(vehicle.equals(vehicle)).isTrue();
            assertThat(vehicle.equals(vehicle2)).isTrue();
            assertThat(vehicle.equals(vehicleWithoutSameProductionYear)).isFalse();
            assertThat(vehicle.equals(vehicleWithoutSameModel)).isFalse();
            assertThat(vehicle.equals(vehicleWithoutSameBrand)).isFalse();
        }
    }

    /**
     * Test the constructor
     * Mock the current year value to 2025 for futures uses
     * Check if an IllegalArgumentException is thrown when the production year is greater than the current year
     * Check if an IllegalArgumentException is thrown when the production year is less than 1900
     */
    @Tag("agency")
    @DisplayName("Test the constructor")
    @Test
    void testConstructorExceptions() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);

            assertThatThrownBy(() -> new Car("Toyota", "Corolla", 2026, 4))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("L'année de production doit être comprise entre 1900 et l'année actuelle," +
                            " votre année de production est égale à : 2026");

            assertThatThrownBy(() -> new Car("Toyota", "Corolla", 1899, 4))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("L'année de production doit être comprise entre 1900 et l'année actuelle," +
                            " votre année de production est égale à : 1899");
        }
    }
}