package com.microservice.loan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
@Schema(
        name = "Loan Status Update Request",
        description = "Loan status update request object"
)
public class LoanStatusUpdateRequestDto {

    @Schema(
            description = "Loan Status of Loan",
            example = "APPROVED"
    )
    @NotEmpty(message = "Loan status cannot be null or empty")
    @Pattern(regexp = "PENDING|APPROVED|DISBURSED|ACTIVE|COMPLETED|DEFAULTED|IN_ARREARS|REJECTED|CANCELLED|CLOSED|SETTLED", message = "Invalid loan status")
    private String loanStatus;

}