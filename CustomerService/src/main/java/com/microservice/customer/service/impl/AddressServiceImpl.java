package com.microservice.customer.service.impl;

import com.microservice.customer.dto.AddressDto;
import com.microservice.customer.dto.CustomerDto;
import com.microservice.customer.entity.Address;
import com.microservice.customer.entity.Customer;
import com.microservice.customer.exception.AddressNotFoundException;
import com.microservice.customer.exception.CustomerAlreadyExistException;
import com.microservice.customer.exception.CustomerNotFoundException;
import com.microservice.customer.external.AccountService;
import com.microservice.customer.mapper.AddressMapper;
import com.microservice.customer.mapper.CustomerMapper;
import com.microservice.customer.repository.AddressRepository;
import com.microservice.customer.repository.CustomerRepository;
import com.microservice.customer.service.IAddressService;
import com.microservice.customer.service.ICustomerService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements IAddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;



    /**
     * Create a new address with the given information.
     *
     * @param customerId The ID of the customer for whom the address will be created.
     * @param addressDto The address information to create.
     * @throws CustomerNotFoundException if the customer with the given ID is not found.
     */
    @Override
    public void createAddress(String customerId, AddressDto addressDto) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));

        try {
            Address address = AddressMapper.mapToAddress(addressDto, new Address());
            Address savedAddress = addressRepository.save(address);
            customer.addAddress(savedAddress);
            customerRepository.save(customer);
        } catch (Exception e) {
            log.error("Error creating address: {}", e.getMessage());
        }

    }

    /**
     * Retrieve a list of all addresses.
     *
     * @return A list of all addresses.
     */
    @Override
    public List<AddressDto> getAllAddresses() {
        try {
            return addressRepository.findAll().stream().map(address -> AddressMapper.mapToAddressDto(address, new AddressDto())).toList();
        } catch (Exception e) {
            log.error("Error getting all addresses: {}", e.getMessage());
        }
        return List.of();
    }


    /**
     * Retrieve a specific address by its ID.
     *
     * @param addressId The ID of the address to retrieve.
     * @return The AddressDto of the address with the given ID.
     * @throws AddressNotFoundException if the address with the given ID is not found.
     */
    @Override
    public AddressDto getAddress(String addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with given id : " + addressId));
        return AddressMapper.mapToAddressDto(address, new AddressDto());
    }


    /**
     * Retrieve a list of addresses by customer ID.
     *
     * @param customerId The ID of the customer whose addresses to retrieve.
     * @return A list of addresses belonging to the customer.
     * @throws CustomerNotFoundException if the customer with the given ID is not found.
     */
    @Override
    public List<AddressDto> getAddressByCustomerId(String customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));

        return customer.getAddress().stream().map(address -> AddressMapper.mapToAddressDto(address, new AddressDto())).toList();
    }

    /**
     * Update an existing address with the given information.
     *
     * @param addressDto The updated address information.
     * @return True if the update was successful, false otherwise.
     * @throws AddressNotFoundException if the address with the given ID is not found.
     */
    @Override
    public boolean updateAddress(String addressId, AddressDto addressDto) {
        boolean isUpdated = false;

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("Address not found with given id : " + addressDto.getId()));
        try {
            AddressMapper.mapToAddress(addressDto, address);
            addressRepository.save(address);
            isUpdated = true;
        } catch (Exception e) {
            log.error("Error updating address: {}", e.getMessage());
        }
        return isUpdated;
    }

    /**
     * Delete an address by its ID.
     *
     * @param addressId The ID of the address to delete.
     * @return True if the deletion was successful, false otherwise.
     * @throws AddressNotFoundException if the address with the given ID is not found.
     */
    @Override
    public boolean deleteAddress(String customerId, String addressId) {
        Address addressFromDB = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("Address not found with given id : " + addressId));

        try {
//            Customer updatedCustomer = customerRepository.findById(customerId)
//                    .map(customer -> {
//                        List<Address> collect = customer.getAddress().stream().filter(address -> address.getId() != addressId).collect(Collectors.toList());
//                        customer.setAddress(collect);
//                        return customer;
//                    })
//                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));

            Customer updatedCustomer = customerRepository.findById(customerId)
                    .map(customer -> {
                        customer.getAddress().removeIf(address -> address.getId().equals(addressId));
                        return customer;
                    })
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));

            customerRepository.save(updatedCustomer);
            addressRepository.deleteById(addressId);
            return true;
        } catch (Exception e) {
            log.error("Error deleting address: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Delete addresses by customer ID.
     *
     * @param customerId The ID of the customer whose addresses to delete.
     * @return True if the deletion was successful, false otherwise.
     * @throws CustomerNotFoundException if the customer with the given ID is not found.
     */
    @Override
    public boolean deleteAddressByCustomerId(String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));
        try {
            addressRepository.deleteAll(customer.getAddress());
            customer.setAddress(List.of());
            customerRepository.save(customer);
            return true;
        }catch (Exception e) {
            log.error("Error deleting address: {}", e.getMessage());
        }
        return false;
    }
}
