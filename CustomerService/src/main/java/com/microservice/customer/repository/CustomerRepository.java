package com.microservice.customer.repository;

import com.microservice.customer.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Optional<Customer> findCustomerByEmail(String email);
    Optional<Customer> findCustomerByPhone(String phone);
    Optional<Customer> findCustomerByEmailOrPhone(String email,String phone);


}
