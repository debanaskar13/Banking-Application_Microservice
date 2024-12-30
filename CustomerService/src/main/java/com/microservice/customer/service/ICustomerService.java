package com.microservice.customer.service;

import com.microservice.customer.dto.CustomerDto;
import com.microservice.customer.dto.CustomerUpdateDto;

import java.util.List;

public interface ICustomerService {

    /**
     * Create a new customer with the given information.
     *
     * @param customerDto The customer information to create.
     */
    void createCustomer(CustomerDto customerDto);

    /**
     * Update an existing customer with the given ID.
     *
     * @param customerId The ID of the customer to update.
     * @param customerUpdateDto The updated customer information.
     */
    boolean updateCustomer(String customerId, CustomerUpdateDto customerUpdateDto);

    /**
     * Retrieve a specific customer by their ID.
     *
     * @param customerId The ID of the customer to retrieve.
     * @return The customer with the given ID.
     */
    CustomerDto getCustomer(String customerId);

    /**
     * Retrieve a list of all customers in the database.
     *
     * @return A list of all customers.
     */
    List<CustomerDto> getAllCustomers();

    /**
     * Delete a customer by their ID.
     *
     * @param customerId The ID of the customer to delete.
     */
    boolean deleteCustomer(String customerId);

    /**
     * Retrieve a customer by their email address.
     *
     * @param email The email address of the customer to retrieve.
     * @return The customer with the given email address.
     */
    CustomerDto getCustomerByEmail(String email);

    /**
     * Check if a customer exists by their ID.
     *
     * @param customerId The ID of the customer to check.
     * @return True if the customer exists, false otherwise.
     */
    boolean isCustomerExist(String customerId);
}
