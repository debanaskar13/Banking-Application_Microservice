package com.microservice.customer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
    name = "Customer",
    description = "Schema to hold Customer Information"
)
public class CustomerDto {

    @Schema(
            description = "Unique identifier for the customer",
            example = "123e4567-e89b-12d3-a456-426655440000"
    )
    private String id;

    @Schema(
            description = "First name of the customer",
            example = "John"
    )
    @NotEmpty(message = "First name can not be null or empty")
    @Size(min = 3, message = "First name must be at least 3 characters long")
    @Size(max = 50, message = "First name must be at most 50 characters long")
    private String firstName;

    @Schema(
            description = "Last name of the customer",
            example = "Doe"
    )
    @NotEmpty(message = "Last name can not be null or empty")
    @Size(min = 3, message = "Last name must be at least 3 characters long")
    @Size(max = 50, message = "Last name must be at most 50 characters long")
    private String lastName;

    @Schema(
            description = "Email address of the customer",
            example = "QK0qI@example.com"
    )
    @NotEmpty(message = "Email can not be null or empty")
    @Email(message = "Email is not valid")
    private String email;

    @Schema(
            description = "Phone number of the customer",
            example = "1234567890"
    )
    @NotEmpty(message = "Phone can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    private String phone;

    @Schema(
            description = "Address of the customer"
    )
    private List<AddressDto> address = new ArrayList<>();

    @Schema(
            description = "Date of birth of the customer",
            example = "28/10/1994"
    )
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d{2})$", message = "Invalid date format. Expected dd/MM/yyyy and must be after 1900 and before current year")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotEmpty(message = "Date cannot be null")
    private String dateOfBirth;

    @Schema(
            description = "Gender of the customer",
            example = "MALE"
    )
    @NotEmpty(message = "Gender can not be null or empty")
    @Pattern(regexp = "MALE|FEMALE|OTHERS", message = "Invalid gender value")
    private String gender;

}
