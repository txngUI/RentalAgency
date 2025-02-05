package com.rentalagency.agency;

import com.rentalagency.criterions.BrandCriterion;
import com.rentalagency.criterions.MaxPriceCriterion;
import com.rentalagency.util.TimeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class RentalAgencyTest {
    RentalAgency rentalAgency;
    RentalAgency rentalAgencyEmpty;
    Motorbike motorbike;
    Calendar birth;
    Customer customer;
    Vehicle car;

    /**
     * Set up the test environment
     * Create a list of vehicles
     * Create a motorbike
     * Create a car
     * Create a customer
     * Create a rental agency with the list of vehicles created which contains the motorbike
     * Create an empty rental agency
     */
    @BeforeEach
    void setUp() {
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);
            ArrayList<Vehicle> vehicles = new ArrayList<>();
            motorbike = new Motorbike("Yamaha", "FZ6", 2006, 600);
            vehicles.add(motorbike);

            car = new Car("Toyota", "Corolla", 2019, 4);

            birth = Calendar.getInstance();
            birth.set(2004, Calendar.JANUARY, 2);
            customer = new Customer(birth.getTime(), "David", "Tanguy");

            rentalAgency = new RentalAgency(vehicles);

            rentalAgencyEmpty = new RentalAgency();
        }
    }

    /**
     * Test to add a vehicle to the list of the rental agency
     * Add a vehicle to the list of the rental agency and check if the vehicle is in the list
     * Add the same vehicle to the list of the rental agency and check if the vehicle is not added
     */
    @Tag("agency")
    @DisplayName("Test to add a vehicle to the list of the rental agency")
    @Test
    void add() {

        // Then
        assertThat(rentalAgencyEmpty.add(car)).isTrue();
        assertThat(rentalAgencyEmpty.getVehicles())
                .hasSize(1)
                .contains(car);

        // When
        rentalAgencyEmpty.add(car);

        // Then
        assertThat(rentalAgencyEmpty.add(car)).isFalse();
        assertThat(rentalAgencyEmpty.getVehicles())
                .hasSize(1)
                .contains(car)
                .doesNotHaveDuplicates();
    }

    /**
     * Test to remove a vehicle from the list of the rental agency
     * Remove a vehicle from the list of the rental agency and check if the vehicle is not in the list
     * Remove the same vehicle from the list of the rental agency and check if an exception is thrown
     */
    @Tag("agency")
    @DisplayName("Test to remove a vehicle from the list of the rental agency")
    @Test
    void remove() {
        // When
        rentalAgency.remove(motorbike);

        // Then
        assertThat(rentalAgency.getVehicles())
                .doesNotContain(motorbike)
                .isEmpty();

        assertThatThrownBy(() -> rentalAgency.remove(motorbike))
                .isInstanceOf(UnknownVehicleException.class)
                .hasMessage("Vehicle not found in the agency: " + motorbike.toString());
    }

    /**
     * Test to get the list of vehicles of the rental agency
     * Check if the list of vehicles contains the motorbike and has a size of 1
     */
    @Tag("agency")
    @DisplayName("Test to get the list of vehicles of the rental agency")
    @Test
    void getVehicles() {
        // When
        List<Vehicle> vehicles = rentalAgency.getVehicles();

        // Then
        assertThat(vehicles)
                .contains(
                        motorbike
                )
                .hasSize(1);
    }

    /**
     * Test to select vehicles that satisfy the given criterion
     * Add a vehicle to the list of the rental agency which able us to select one of several vehicles
     * Select vehicles that satisfy the brand criterion and check if the list of vehicles contains only the vehicle
     * added
     * Select vehicles that satisfy the max price criterion and check if the list of vehicles contains only the vehicle
     * added
     */
    @Tag("agency")
    @DisplayName("Test to select vehicles that satisfy the given criterion")
    @Test
    void select() {
        // Given
        List<Vehicle> expectedVehicles = List.of(car);
        BrandCriterion brandCriterion = new BrandCriterion("Toyota");
        MaxPriceCriterion maxPriceCriterion = new MaxPriceCriterion(100);

        // Then
        rentalAgency.add(car);

        // When
        assertThat(rentalAgency.select(brandCriterion))
                .contains(car)
                .hasSize(1)
                .isEqualTo(expectedVehicles);

        assertThat(rentalAgency.select(maxPriceCriterion))
                .contains(car)
                .hasSize(1)
                .isEqualTo(expectedVehicles);
    }

    /**
     * Test to print the vehicles that satisfy the given criterion
     * Add a vehicle to the list of the rental agency which able us to print the selected vehicles
     * Print the selected vehicles that satisfy the brand criterion and check if the output contains the vehicle added
     * and does not contain the motorbike
     */
    @Tag("agency")
    @DisplayName("Test to print the selected vehicles")
    @Test
    void printSelectedVehicles() {
        // Given
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // When
        rentalAgency.add(car);
        rentalAgency.printSelectedVehicles(new BrandCriterion("Toyota"));

        // Then
        assertThat(outputStream.toString().trim())
                .contains(car.toString())
                .doesNotContain(motorbike.toString());

        System.setOut(originalOut);
    }

    /**
     * Test to rent a vehicle
     * Check if an exception is thrown when the vehicle is not found in the agency
     * Check if an exception is thrown when the customer already rented a vehicle
     * Check if an exception is thrown when the vehicle is already rented
     * Check if the daily rental price of the vehicle is returned when the vehicle is rented
     */
    @Tag("agency")
    @DisplayName("Test to rent a vehicle")
    @Test
    void rentVehicle() {
        // Given
        Customer anOtherCustomer = new Customer(birth.getTime(), "John", "Doe");

        // Then
        assertThatThrownBy(() -> rentalAgency.rentVehicle(customer, car))
                .isInstanceOf(UnknownVehicleException.class)
                .hasMessage("Vehicle not found in the agency: " + car);

        // When
        rentalAgency.add(car);
        rentalAgency.rentVehicle(customer, car);

        // Then
        assertThatThrownBy(() -> rentalAgency.rentVehicle(customer, car))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Customer already rented a vehicle");

        // When
        rentalAgency.returnVehicle(customer);

        // Then
        try (MockedStatic<TimeProvider> mockedTimeProvider = mockStatic(TimeProvider.class)) {
            mockedTimeProvider.when(TimeProvider::currentYearValue).thenReturn(2025);
            assertThat(rentalAgency.rentVehicle(anOtherCustomer, motorbike))
                    .isEqualTo(motorbike.dailyRentalPrice());
        }

        assertThatThrownBy(() -> rentalAgency.rentVehicle(customer, motorbike))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Vehicle already rented");
    }

    /**
     * Test if a customer rented a vehicle
     * Check if the customer rented a vehicle
     * Check if an other customer did not rent a vehicle
     */
    @Tag("agency")
    @DisplayName("Test if a customer rented a vehicle")
    @Test
    void aVehicleRentedBy() {
        // Given
        Customer jDoe = new Customer(birth.getTime(), "John", "Doe");

        // When
        rentalAgency.rentVehicle(customer, motorbike);

        // Then
        assertThat(rentalAgency.aVehicleRentedBy(customer)).isTrue();
        assertThat(rentalAgency.aVehicleRentedBy(jDoe)).isFalse();
    }

    /**
     * Test if a vehicle is rented
     * Check if the vehicle is rented
     * Check if an other vehicle is not rented
     */
    @Tag("agency")
    @DisplayName("Test if a vehicle is rented")
    @Test
    void aVehicleRented() {
        // When
        rentalAgency.rentVehicle(customer, motorbike);

        // Then
        assertThat(rentalAgency.aVehicleRented(motorbike)).isTrue();
        assertThat(rentalAgency.aVehicleRented(car)).isFalse();
    }

    /**
     * Test to return a vehicle
     * Return a vehicle rented by a customer and check if the vehicle is not rented by the customer and if the vehicle
     * is not rented
     */
    @Tag("agency")
    @DisplayName("Test to return a vehicle")
    @Test
    void returnVehicle() {
        // When
        rentalAgency.rentVehicle(customer, motorbike);
        rentalAgency.returnVehicle(customer);

        // Then
        assertThat(rentalAgency.aVehicleRentedBy(customer)).isFalse();
        assertThat(rentalAgency.aVehicleRented(motorbike)).isFalse();

        // When
        rentalAgency.returnVehicle(customer);

        // Then
        assertThat(rentalAgency.aVehicleRentedBy(customer)).isFalse();
    }

    /**
     * Test to get all rented vehicles
     * Check if the list of rented vehicles is empty
     * Rent a vehicle and check if the list of rented vehicles contains the vehicle rented
     */
    @Tag("agency")
    @DisplayName("Test to get all rented vehicles")
    @Test
    void allRentedVehicles() {
        // Then
        assertThat(rentalAgency.allRentedVehicles()).isEmpty();

        // When
        rentalAgency.rentVehicle(customer, motorbike);

        // Then
        assertThat(rentalAgency.allRentedVehicles())
                .contains(motorbike)
                .hasSize(1);
    }
}