package com.rentalagency.agency;

import java.util.*;
import java.util.function.Predicate;

public class RentalAgency {
    private List<Vehicle> vehicles;
    private Map<Customer, Vehicle> rentedVehicles;
    
    /**
     * Constructor : creates a new RentalAgency with the given list of vehicles
     * @param vehicles the list of vehicles
     */
    public RentalAgency(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
        this.rentedVehicles = new HashMap<>();
    }

    /**
     * Constructor : creates a new RentalAgency with an empty list of vehicles
     */
    public RentalAgency() {
        this(new ArrayList<>());
    }

    /**
     * Add a vehicle to the list of vehicles
     * @param vehicle the vehicle to add
     * @return true if the vehicle was added, false if it was already in the list
     */
    public boolean add(Vehicle vehicle) {
        if (vehicles.contains(vehicle)) return false;
        return vehicles.add(vehicle);
    }

    /**
     * Remove a vehicle from the list of vehicles
     * @param vehicle : the vehicle to remove
     */
    public void remove(Vehicle vehicle) {
        if (!vehicles.contains(vehicle)) throw new UnknownVehicleException(vehicle);
        vehicles.remove(vehicle);
    }

    /**
     * Get the list of vehicles
     * @return the list of vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Select vehicles that satisfy the given criterion
     * @param criterion the criterion to satisfy
     * @return the list of vehicles that satisfy the criterion
     */
    public List<Vehicle> select(Predicate<Vehicle> criterion) {
        List<Vehicle> selectedVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (criterion.test(vehicle)) selectedVehicles.add(vehicle);
        }
        return selectedVehicles;
    }

    /**
     * Print the vehicles that satisfy the given criterion
     * @param criterion the criterion to satisfy
     */
    public void printSelectedVehicles(Predicate<Vehicle> criterion) {
        select(criterion).forEach(System.out::println);
    }

    /**
     * Rent a vehicle to a customer
     * @param customer : the customer
     * @param vehicle : the vehicle
     * @return the daily rental price of the vehicle
     * @throws UnknownVehicleException : if the vehicle is not in the list of vehicles
     * @throws IllegalStateException : if the customer already rented a vehicle or if the vehicle is already rented
     */
    public double rentVehicle(Customer customer, Vehicle vehicle) throws UnknownVehicleException, IllegalStateException {
        if (!vehicles.contains(vehicle)) throw new UnknownVehicleException(vehicle);
        if (rentedVehicles.containsKey(customer)) throw new IllegalStateException("Customer already rented a vehicle");
        if (rentedVehicles.containsValue(vehicle)) throw new IllegalStateException("Vehicle already rented");
        rentedVehicles.put(customer, vehicle);
        return vehicle.dailyRentalPrice();
    }

    /**
     * Check if a customer rented a vehicle
     * @param customer : the customer
     * @return true if the customer rented a vehicle, false otherwise
     */
    public boolean aVehicleRentedBy(Customer customer) {
        return rentedVehicles.containsKey(customer);
    }

    /**
     * Check if a vehicle is rented
     * @param vehicle : the vehicle
     * @return true if the vehicle is rented, false otherwise
     */
    public boolean aVehicleRented(Vehicle vehicle) {
        return rentedVehicles.containsValue(vehicle);
    }

    /**
     * Return a vehicle rented by a customer
     * @param customer : the customer
     */
    public void returnVehicle(Customer customer) {
        if (aVehicleRentedBy(customer)) rentedVehicles.remove(customer);
    }

    /**
     * Return all rented vehicles
     * @return the collection of rented vehicles
     */
    public Collection<Vehicle> allRentedVehicles() {
        return rentedVehicles.values();
    }
}
