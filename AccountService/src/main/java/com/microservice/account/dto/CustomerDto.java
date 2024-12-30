package com.microservice.account.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Customer",
        description = "Schema to hold Customer information"
)
public class CustomerDto {

    @Schema(
            description = "Customer Id of the customer"
    )
    private String id;


    @Schema(
            description = "Customer First Name of the customer", example = "John"
    )
    private String firstName;

    @Schema(
            description = "Customer Last Name of the customer", example = "Doe"
    )
    private String lastName;

    @Schema(
            description = "Customer Email of the customer", example = "p6Ft9@example.com"
    )
    private String email;

    @Schema(
            description = "Customer Phone Number of the customer", example = "9334567890"
    )
    private String phone;

    @Schema(
            description = "List of Accounts of the customer"
    )
    private List<AccountDto> accounts;
}
