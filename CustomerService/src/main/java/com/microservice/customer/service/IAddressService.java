package com.microservice.customer.service;

import com.microservice.customer.dto.AddressDto;
import org.bson.types.ObjectId;

import java.util.List;

public interface IAddressService {

    /**
     * Create a new address with the given information.
     *
     * @param addressDto The address information to create.
     */
    void createAddress(String customerId,AddressDto addressDto);

    /**
     * Retrieve a list of all addresses.
     *
     * @return A list of all addresses.
     */
    List<AddressDto> getAllAddresses();

    /**
     * Retrieve a specific address by its ID.
     *
     * @param addressId The ID of the address to retrieve.
     * @return The address with the given ID.
     */
    AddressDto getAddress(String addressId);

    /**
     * Retrieve a list of addresses by customer ID.
     *
     * @param customerId The ID of the customer whose addresses to retrieve.
     * @return A list of addresses belonging to the customer.
     */
    List<AddressDto> getAddressByCustomerId(String customerId);

    /**
     * Update an existing address with the given information.
     *
     * @param addressDto The updated address information.
     * @return True if the update was successful, false otherwise.
     */
    boolean updateAddress(String addressId, AddressDto addressDto);

    /**
     * Delete an address by its ID.
     *
     * @param addressId The ID of the address to delete.
     * @return True if the deletion was successful, false otherwise.
     */
    boolean deleteAddress(String customerId, String addressId);

    /**
     * Delete addresses by customer ID.
     *
     * @param customerId The ID of the customer whose addresses to delete.
     * @return True if the deletion was successful, false otherwise.
     */
    boolean deleteAddressByCustomerId(String customerId);
}
