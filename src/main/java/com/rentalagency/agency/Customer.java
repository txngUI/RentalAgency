package com.rentalagency.agency;

import java.util.Date;

public class Customer {
    private Date birth;
    private String name;
    private String surname;

    /**
     * Constructor : creates a new Customer with the given birth, name and surname
     * @param birth the birth
     * @param name the name
     * @param surname the surname
     */
    public Customer(Date birth, String name, String surname) {
        this.birth = birth;
        this.name = name;
        this.surname = surname;
    }
}
