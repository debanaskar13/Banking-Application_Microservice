package com.microservice.loan.dto;

import com.microservice.loan.dto.external.LoanPaymentResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
@Schema(
    name = "LoanResponseDto",
    description = "Loan Response Dto"
)
public class LoanResponseDto {

    @Schema(
        description = "Loan Id",example = "1475125"
    )
    private long loanId;

    @Schema(
        description = "Customer Id",example = "1234567890"
    )
    private String customerId;

    @Schema(
        description = "Loan Type of Loan",example = "PERSONAL"
    )
    private String loanType;

    @Schema(
        description = "Loan Amount of Loan",example = "10000"
    )
    private double loanAmount;

    @Schema(
        description = "Interest Rate of Loan",example = "10"
    )
    private double interestRate;

    @Schema(
        description = "Loan Status of Loan",example = "ACTIVE"
    )
    private String loanStatus;

    @Schema(
        description = "Loan Duration in months of Loan",example = "12"
    )
    private int loanDuration;

    @Schema(
        description = "Start Date of Loan",example = "01/01/2024"
    )
    private LocalDate startDate;

    @Schema(
        description = "EMI of Loan",example = "10000"
    )
    private double emi;

    @Schema(
        description = "Amount Paid of Loan",example = "50000"
    )
    private double amountPaid;

    @Schema(
        description = "Outstanding Amount of Loan",example = "5000"
    )
    private double outstandingAmount;

    @Schema(
        description = "List of payments of Loan"
    )
    private List<LoanPaymentResponseDto> payments = new ArrayList<>();

    @Schema(
        description = "Last Payment Date of Loan",example = "01/01/2024"
    )
    private LocalDate lastPaymentDate;

    @Schema(
        description = "Repayment Due Date of Loan",example = "01/01/2024"
    )
    private LocalDate repaymentDueDate;
}
