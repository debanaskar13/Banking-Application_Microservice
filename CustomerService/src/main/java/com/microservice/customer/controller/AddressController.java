package com.microservice.customer.controller;


import com.microservice.customer.annotation.ApiResponse_200_417_500;
import com.microservice.customer.annotation.ApiResponse_200_500;
import com.microservice.customer.annotation.ApiResponse_201_500;
import com.microservice.customer.constants.AppConstants;
import com.microservice.customer.dto.AddressDto;
import com.microservice.customer.dto.CustomerDto;
import com.microservice.customer.dto.ResponseDto;
import com.microservice.customer.repository.CustomerRepository;
import com.microservice.customer.service.IAddressService;
import com.microservice.customer.service.ICustomerService;
import com.microservice.customer.service.impl.CustomerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(
        name = "CRUD Rest APIs for Address",
        description = "CRUD REST APIs to CREATE, READ, UPDATE and DELETE customer address information."
)
public class AddressController {

    private final IAddressService addressService;
    private final CustomerServiceImpl customerServiceImpl;
    private final CustomerRepository customerRepository;


    @Operation(
            summary = "Create a new address REST API",
            description = "REST API to create a new address with the given information."
    )
    @PostMapping("/customer/{customerId}/address/create")
    @ApiResponse_201_500
    public ResponseEntity<ResponseDto> createAddress(@PathVariable String customerId, @RequestBody @Valid AddressDto addressDto) {
        addressService.createAddress(customerId,addressDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AppConstants.STATUS_201, AppConstants.MESSAGE_201_ADDRESS));
    }

    @Operation(
            summary = "Fetch all address for a customer REST API",
            description = "REST API to fetch all address for a customer by customer id"
    )
    @GetMapping("/customer/{customerId}/address")
    @ApiResponse_200_500
    public ResponseEntity<List<AddressDto>> getAllAddressByCustomer(@PathVariable String customerId) {
        return new ResponseEntity<>(addressService.getAddressByCustomerId(customerId), HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch address REST API",
            description = "REST API to fetch address by address id"
    )
    @GetMapping("/address/{addressId}")
    @ApiResponse_200_500
    public ResponseEntity<AddressDto> getAddress(@PathVariable String addressId) {
        return new ResponseEntity<>(addressService.getAddress(addressId), HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch all address REST API",
            description = "REST API to fetch all address from the database"
    )
    @GetMapping("/address")
    @ApiResponse_200_500
    public ResponseEntity<List<AddressDto>> getAllAddress() {
        return new ResponseEntity<>(addressService.getAllAddresses(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update address REST API",
            description = "REST API to update address by address id"
    )
    @PutMapping("/address/{addressId}")
    @ApiResponse_200_417_500
    public ResponseEntity<ResponseDto> updateAddress(@PathVariable String addressId  , @RequestBody @Valid AddressDto addressDto) {
        boolean isUpdated = addressService.updateAddress(addressId,addressDto);
        if (isUpdated) {
            return new ResponseEntity<>(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AppConstants.STATUS_417, AppConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete address REST API",
            description = "REST API to delete address by address id"
    )
    @DeleteMapping("/address/delete")
    @ApiResponse_200_417_500
    public ResponseEntity<ResponseDto> deleteAddress(@RequestParam(value = "addressId") String addressId, @RequestParam(value = "customerId") String customerId) {
        boolean isDeleted = addressService.deleteAddress(customerId , addressId);
        if (isDeleted) {
            return new ResponseEntity<>(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AppConstants.STATUS_417, AppConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Delete all address for a customer REST API",
            description = "REST API to delete all address for a customer by customer id"
    )
    @DeleteMapping("/customer/{customerId}/address")
    @ApiResponse_200_417_500
    public ResponseEntity<ResponseDto> deleteAllAddressByCustomer(@PathVariable String customerId) {
        boolean isDeleted = addressService.deleteAddressByCustomerId(customerId);
        if (isDeleted) {
            return new ResponseEntity<>(new ResponseDto(AppConstants.STATUS_200, AppConstants.MESSAGE_200), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AppConstants.STATUS_417, AppConstants.MESSAGE_417_DELETE));
        }
    }

}
