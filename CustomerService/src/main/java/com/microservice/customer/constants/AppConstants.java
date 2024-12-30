package com.microservice.customer.constants;

public class AppConstants {

    private AppConstants() {

//        Restrict Initiation
    }

    public static final String STATUS_201 = "201";
    public static final String MESSAGE_201 = "Customer created successfully";
    public static final String MESSAGE_201_ADDRESS = "Address created successfully";
    public static final String STATUS_200 = "200";
    public static final String MESSAGE_200 = "Request processed successfully";
    public static final String MESSAGE_200_EXIST = "Customer Exists";
    public static final String STATUS_500 = "500";
    public static final String MESSAGE_500 = "An error occurred. Please try again or contact Dev team";
    public static final String STATUS_417 = "417";
    public static final String MESSAGE_417_UPDATE = "Update operation failed. Please try again or contact Dev team";
    public static final String MESSAGE_417_DELETE = "Delete operation failed. Please try again or contact Dev team";
    public static final String STATUS_404 = "404";
    public static final String MESSAGE_404 = "Customer not found";
    public static final String MESSAGE_404_ADDRESS = "Address not found";
    public static final String STATUS_400 = "400";
    public static final String MESSAGE_400 = "Invalid request";

}
