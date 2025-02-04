package com.rentalagency.agency;

public class UnknownVehicleException extends RuntimeException {
    private Vehicle vehicle;

    /**
     * Constructor : creates a new UnknownVehicleException with the given vehicle
     * @param vehicle the vehicle
     */
    public UnknownVehicleException(Vehicle vehicle) {
        super();
        this.vehicle = vehicle;
    }

    /**
     * Get the vehicle
     * @return the vehicle
     */
    @Override
    public String getMessage() {
        return "Vehicle not found in the agency: " + vehicle.toString();
    }
}
