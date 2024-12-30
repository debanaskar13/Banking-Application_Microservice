package com.microservice.customer.service.impl;

import com.microservice.customer.dto.CustomerUpdateDto;
import com.microservice.customer.entity.Address;
import com.microservice.customer.entity.Customer;
import com.microservice.customer.dto.CustomerDto;
import com.microservice.customer.exception.CustomerAlreadyExistException;
import com.microservice.customer.external.AccountService;
import com.microservice.customer.mapper.CustomerMapper;
import com.microservice.customer.exception.CustomerNotFoundException;
import com.microservice.customer.repository.CustomerRepository;
import com.microservice.customer.service.ICustomerService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AccountService accountService;

    /**
     * Create a new customer with the given information.
     *
     * @param customerDto The customer information to create.
     */
    @Override
    public void createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer = customerMapper.dtoToCustomer(customerDto, customer);
        Optional<Customer> customerByEmailOrPhone = customerRepository.findCustomerByEmailOrPhone(customerDto.getEmail(), customerDto.getPhone());
        if(customerByEmailOrPhone.isPresent()){
            throw new CustomerAlreadyExistException("Customer with email or phone already exists");
        }
        try {
            Customer savedCustomer = customerRepository.save(customer);
        } catch (Exception e) {
            log.error("Error creating customer: {}", e.getMessage());
        }
    }

    /**
     * Update an existing customer with the given ID.
     *
     * @param customerId The ID of the customer to update.
     * @param customerUpdateDto The updated customer information.
     */

    @Override
    public boolean updateCustomer(String customerId, CustomerUpdateDto customerUpdateDto) {

        boolean isUpdated = false;

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        try {
            customer = updateCustomer(customerUpdateDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        } catch (Exception e) {
            log.error("Error updating customer: {}", e.getMessage());
        }
        return isUpdated;
    }

    private Customer updateCustomer(CustomerUpdateDto customerUpdateDto, Customer customer) {

        customer.setFirstName(customerUpdateDto.getFirstName() == null ? customer.getFirstName() : customerUpdateDto.getFirstName());
        customer.setLastName(customerUpdateDto.getLastName() == null? customer.getLastName() : customerUpdateDto.getLastName());
        customer.setPhone(customerUpdateDto.getPhone() == null? customer.getPhone() : customerUpdateDto.getPhone());
        customer.setDateOfBirth(customerUpdateDto.getDateOfBirth() == null? customer.getDateOfBirth() : customerUpdateDto.getDateOfBirth());
        customer.setGender(customerUpdateDto.getGender() == null? customer.getGender() : customerUpdateDto.getGender());

        return customer;
    }


    /**
     * Retrieve a specific customer by their ID.
     *
     * @param customerId The ID of the customer to retrieve.
     * @return The customer with the given ID.
     */
    @Override
    public CustomerDto getCustomer(String customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return customerMapper.customerToDto(customer, new CustomerDto());
    }

    /**
     * Retrieve a list of all customers in the database.
     *
     * @return A list of all customers.
     */
    @Override
    public List<CustomerDto> getAllCustomers() {

        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(customer -> customerMapper.customerToDto(customer, new CustomerDto())).toList();
    }


    /**
     * Delete a customer by their ID.
     *
     * @param customerId The ID of the customer to delete.
     */
    @Override
    @Transactional
    public boolean deleteCustomer(String customerId) {
        boolean isDeleted = false;
        CustomerDto customer = getCustomer(customerId);
        try {
            if (customer != null) {
                accountService.deleteAccounts(customerId);
                customerRepository.deleteById(customerId);
                isDeleted = true;
            }
        }catch (FeignException.NotFound e) {
            log.info("No Accounts Found for customer {}", customerId);
            customerRepository.deleteById(customerId);
            isDeleted = true;
        }catch(FeignException.InternalServerError e) {

            log.error("Error deleting accounts: {}", e.getMessage());
            throw new RuntimeException("Error deleting accounts");

        }catch (FeignException e) {
            log.error("Error deleting accounts : {}", e.getMessage());
            throw new RuntimeException("Error deleting accounts");

        }catch (Exception e) {
            log.error("Error deleting customer: {}", e.getMessage());
            throw new RuntimeException("Error deleting customer");
        }
        return isDeleted;
    }


    /**
     * Retrieve a customer by their email address.
     *
     * @param email The email address of the customer to retrieve.
     * @return The customer with the given email address.
     */
    @Override
    public CustomerDto getCustomerByEmail(String email) {

        Customer customer = customerRepository.findCustomerByEmail(email).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        return customerMapper.customerToDto(customer,new CustomerDto());
    }

    /**
     * Check if a customer exists by their ID.
     *
     * @param customerId The ID of the customer to check.
     * @return True if the customer exists, false otherwise.
     */
    @Override
    public boolean isCustomerExist(String customerId) {
        return this.customerRepository.findById(customerId).isPresent();
    }
}
