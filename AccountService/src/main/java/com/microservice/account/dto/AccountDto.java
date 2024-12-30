package com.microservice.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(
    name = "Account",
    description = "Schema to hold Account information"
)
public class AccountDto {

    @Schema(
            description = "Account number of the account",
            example = "1234567890"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    private String accountNumber;

    @Schema(
            description = "Account type of the account",
            example = "Savings"
    )
    @NotEmpty(message = "Account type cannot be null or empty")
    private String accountType;

    @Schema(
            description = "Account Branch of the account",
            example = "Kolkata"
    )
    @NotEmpty(message = "Branch cannot be null or empty")
    private String branch;

    @Schema(
            description = "Account Status of the account",
            example = "ACTIVE"
    )
    private String status;
}
