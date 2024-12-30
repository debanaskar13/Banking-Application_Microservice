package com.microservice.loan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
@Schema(
    name = "LoanRequestDto",
    description = "Loan request object"
)
public class LoanRequestDto {

    @Schema(
            description = "Customer ID for the Loan",example = "1234567890"
    )
    @NotEmpty(message = "Customer ID cannot be null or empty")
    private String customerId;

    @Schema(
            description = "Loan type of the Loan",example = "PERSONAL"
    )
    @NotEmpty(message = "Loan type cannot be null or empty")
    private String loanType;

    @Schema(
            description = "Loan amount of the Loan",example = "10000"
    )
    @Positive(message = "Loan amount must be a positive number")
    private double loanAmount;

    @Schema(
            description = "Interest rate of the Loan",example = "10"
    )
    @Positive(message = "Interest rate must be a positive number")
    private double interestRate;

    @Schema(
            description = "Loan duration in months of the Loan",example = "12"
    )
    @Positive(message = "Loan duration must be a positive number")
    private int loanDuration;

}
