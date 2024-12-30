package com.microservice.customer.mapper;

import com.microservice.customer.dto.AddressDto;
import com.microservice.customer.entity.Address;
import com.microservice.customer.entity.Customer;
import com.microservice.customer.dto.CustomerDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {


    public Customer dtoToCustomer(CustomerDto customerDto,Customer customer) {
        customer.setFirstName(customerDto.getFirstName() == null ? customer.getFirstName() : customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName() == null? customer.getLastName() : customerDto.getLastName());
        customer.setEmail(customerDto.getEmail() == null? customer.getEmail() : customerDto.getEmail());
        customer.setPhone(customerDto.getPhone() == null? customer.getPhone() : customerDto.getPhone());
        customer.setAddress(customerDto.getAddress() == null ? List.of() : customerDto.getAddress().stream().map(addressDto -> AddressMapper.mapToAddress(addressDto, new Address())).toList());
        customer.setDateOfBirth(customerDto.getDateOfBirth() == null? customer.getDateOfBirth() : customerDto.getDateOfBirth());
        customer.setGender(customerDto.getGender());
        return customer;
    }

    public CustomerDto customerToDto(Customer customer,CustomerDto customerDto) {
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhone(customer.getPhone());
        if(customer.getAddress() != null){
            customerDto.setAddress(customer.getAddress().stream().map(address -> AddressMapper.mapToAddressDto(address, new AddressDto())).toList());

        }else{
            customerDto.setAddress(List.of());
        }
        customerDto.setDateOfBirth(customer.getDateOfBirth());
        customerDto.setGender(customer.getGender());
        return customerDto;
    }


}
