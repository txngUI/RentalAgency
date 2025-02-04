package com.rentalagency.agency;

public interface Vehicle {
    String getBrand();
    String getModel();
    int getProductionYear();
    double dailyRentalPrice();
    boolean equals(Object o);
    String toString();
}
