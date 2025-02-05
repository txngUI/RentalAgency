package com.rentalagency.agency;

public class Motorbike extends AbstractVehicle {
    private int cylinderCapacity;

    /**
     * Constructor : creates a new Motorbike with the given brand, model, production year and cylinder capacity
     * @param brand : the brand of the motorbike
     * @param model : the model of the motorbike
     * @param productionYear : the production year of the motorbike
     * @param cylinderCapacity : the cylinder capacity of the motorbike
     */
    public Motorbike(String brand, String model, int productionYear,int cylinderCapacity) {
        super(brand, model, productionYear);
        if (cylinderCapacity < 50) {
            throw new IllegalArgumentException("La cylindrée doit être supérieure à 50, votre cylindrée est égale à : " + cylinderCapacity);
        }
        this.cylinderCapacity = cylinderCapacity;
    }

    /**
     * Get the cylinder capacity of the motorbike
     * @return the cylinder capacity of the motorbike
     */
    @Override
    public double dailyRentalPrice() {
        return 0.25 * cylinderCapacity;
    }

    /**
     * Get the cylinder capacity of the motorbike
     * @return the cylinder capacity of the motorbike
     */
    @Override
    public String toString() {
        return "Motorbike, " + getBrand() + ", " + getModel() + ", " + getProductionYear() + ", " +  cylinderCapacity + "cm3 :" + dailyRentalPrice() + "€/day";
    }
}
