package com.rentalagency.agency;

import com.rentalagency.util.TimeProvider;

public class Car extends AbstractVehicle implements Vehicle {
    private String brand;
    private String model;
    private int productionYear;
    private int numberOfSeats;

    /**
     * Constructor : creates a new Car with the given brand, model, production year and number of seats
     * @param brand : the brand of the car
     * @param model
     * @param productionYear
     * @param numberOfSeats
     */
    public Car(String brand, String model, int productionYear, int numberOfSeats) {
        super(brand, model, productionYear);

        if (numberOfSeats < 1) {
            throw new IllegalArgumentException("Le nombre de sièges doit être supérieur à 0, votre nombre de sièges est égal à : " + numberOfSeats);
        }

        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Set the rental price of the car
     * @return the rental price of the car
     */
    @Override
    public double dailyRentalPrice() {
        return isNew() ? 40 * numberOfSeats : 20 * numberOfSeats;
    }

    /**
     * Return the string representation of the car
     * @return the string representation of the car
     */
    @Override
    public String toString() {
        return "Car, " + brand + ", " + model + ", " + productionYear + ", " +  numberOfSeats + " seats :" + dailyRentalPrice() + "€/day";
    }
}
