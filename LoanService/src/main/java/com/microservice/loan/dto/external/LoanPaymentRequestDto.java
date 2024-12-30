package com.microservice.loan.dto.external;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data@AllArgsConstructor@NoArgsConstructor@ToString@Builder
@Schema(
    name = "Loan Payment Request",
    description = "Request to make a payment for a loan"
)
public class LoanPaymentRequestDto {

    @Schema(
        description = "Loan Id",example = "1475125"
    )
    private long loanId;

    @Schema(
        description = "Customer Id",example = "1234567890"
    )
    @NotEmpty(message = "Customer Id cannot be null or empty")
    private String customerId;

    @Schema(
        description = "Amount of the payment",example = "1000"
    )
    @Positive(message = "Amount must be a positive number")
    private double amount;

    @Schema(
        description = "Payment method of the payment",example = "CASH"
    )
    @NotEmpty(message = "Payment method cannot be null or empty")
    private String paymentMethod;

    @Schema(
        description = "Payment date of the payment",example = "01/01/2023"
    )
    @NotEmpty(message = "Payment date cannot be null or empty")
    @PastOrPresent(message = "Payment date must be in the past or present")
    private LocalDate paymentDate;
}
