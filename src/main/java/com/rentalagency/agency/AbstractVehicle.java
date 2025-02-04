package com.rentalagency.agency;

import com.rentalagency.util.TimeProvider;

public abstract class AbstractVehicle implements Vehicle {
    protected String brand;
    protected String model;
    protected int productionYear;

    /**
     * Constructor : creates a new AbstractVehicle with the given brand, model and production year
     * @param brand : the brand of the vehicle
     * @param model : the model of the vehicle
     * @param productionYear : the production year of the vehicle
     */
    public AbstractVehicle(String brand, String model, int productionYear) {
        if (productionYear > 2021 || productionYear < 1900) {
            throw new IllegalArgumentException("L'année de production doit être comprise entre 1900 et l'année actuelle," +
                    " votre année de production est égale à : " + productionYear);
        }

        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
    }

    /**
     * Get the brand of the vehicle
     * @return the brand of the vehicle
     */
    @Override
    public String getBrand() {
        return this.brand;
    }

    /**
     * Get the model of the vehicle
     * @return the model of the vehicle
     */
    @Override
    public String getModel() {
        return this.model;
    }

    /**
     * Get the production year of the vehicle
     * @return the production year of the vehicle
     */
    @Override
    public int getProductionYear() {
        return this.productionYear;
    }

    /**
     * Return the string representation of the vehicle
     * @param o : the object to compare
     * @return true if the object is equal to the vehicle, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    /**
     * Return the string representation of the vehicle
     * @return the string representation of the vehicle
     */
    public boolean isNew() {
        return TimeProvider.currentYearValue() - productionYear <= 5;
    }
}
