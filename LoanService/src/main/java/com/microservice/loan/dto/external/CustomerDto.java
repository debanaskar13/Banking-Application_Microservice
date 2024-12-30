package com.microservice.loan.dto.external;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "Customer",
        description = "Customer object for the Loan Service"
)
public class CustomerDto {

    @Schema(
            name = "customerId",
            description = "Customer ID for the Loan"
    )
    private String id;

    @Schema(
            name = "firstName",
            description = "First name of the Customer"
    )
    private String firstName;

    @Schema(
            name = "lastName",
            description = "Last name of the Customer"
    )
    private String lastName;

    @Schema(
            name = "email",
            description = "Email of the Customer"
    )
    private String email;

    @Schema(
            name = "phoneNumber",
            description = "Phone number of the Customer"
    )
    private String phoneNumber;

}
