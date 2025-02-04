package com.rentalagency.criterions;

import com.rentalagency.agency.Vehicle;

import java.util.function.Predicate;

public class BrandCriterion implements Predicate<Vehicle> {
    private String brand;

    /**
     * Constructor : creates a new BrandCriterion with the given brand
     * @param brand the brand
     */
    public BrandCriterion(String brand) {
        this.brand = brand;
    }

    /**
     * Test if a vehicle satisfies the criterion
     * @param vehicle the input argument
     * @return true if the vehicle satisfies the criterion, false otherwise
     */
    @Override
    public boolean test(Vehicle vehicle) {
        return vehicle.getBrand().equals(brand);
    }
}
