package com.rentalagency.criterions;

import com.rentalagency.agency.Vehicle;

import java.util.function.Predicate;

public class MaxPriceCriterion implements Predicate<Vehicle> {
    private double maxPrice;

    /**
     * Constructor : creates a new MaxPriceCriterion with the given maximum price
     * @param maxPrice the maximum price
     */
    public MaxPriceCriterion(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * Test if a vehicle satisfies the criterion
     * @param vehicle the input argument
     * @return true if the vehicle satisfies the criterion, false otherwise
     */
    @Override
    public boolean test(Vehicle vehicle) {
        return vehicle.dailyRentalPrice() <= maxPrice;
    }
}
