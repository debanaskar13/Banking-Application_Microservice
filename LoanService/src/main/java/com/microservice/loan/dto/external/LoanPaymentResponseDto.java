package com.microservice.loan.dto.external;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Schema(
    name = "Loan Payment Response",
    description = "Loan Payment Response Dto"
)
public class LoanPaymentResponseDto {

    @Schema(
            description = "Loan Payment Id",example = "1475125"
    )
    private String id;

    @Schema(
            description = "Customer Id",example = "1234567890"
    )
    private String customerId;

    @Schema(
            description = "Amount of the payment",example = "1000"
    )
    private double amount;

    @Schema(
            description = "Payment method of the payment",example = "CASH"
    )
    private String paymentMethod;

    @Schema(
            description = "Payment date of the payment",example = "01/01/2023"
    )
    private String paymentDate;

    @Schema(
            description = "Status of the payment",example = "PAID"
    )
    private String status;

    @Schema(
            description = "Transaction Id of the payment",example = "1475125"
    )
    private String transactionId;
}
