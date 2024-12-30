package com.microservice.customer.mapper;

import com.microservice.customer.dto.AddressDto;
import com.microservice.customer.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public static AddressDto mapToAddressDto(Address address,AddressDto addressDto){
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZip(address.getZip());
        return addressDto;
    }

    public static Address mapToAddress(AddressDto addressDto,Address address){
        address.setCity(addressDto.getCity() == null ? address.getCity() : addressDto.getCity());
        address.setState(addressDto.getState() == null ? address.getState() : addressDto.getState());
        address.setCountry(addressDto.getCountry() == null ? address.getCountry() : addressDto.getCountry());
        address.setZip(addressDto.getZip() == null ? address.getZip() : addressDto.getZip());
        return address;
    }

}
