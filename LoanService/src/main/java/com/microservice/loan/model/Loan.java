package com.microservice.loan.model;

import com.microservice.loan.constants.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
@Entity
public class Loan extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private long loanId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "loan_type")
    private String loanType;

    @Column(name = "loan_amount")
    private double loanAmount;

    @Column(name = "interest_rate")
    private double interestRate;

    @Column(name = "loan_status")
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @Column(name = "loan_duration")
    private int loanDuration;

    @Column(name = "start_date")
    private LocalDate startDate;

    private double emi;

    @Column(name = "amount_paid")
    private double amountPaid;

    @Column(name = "outstanding_amount")
    private double outstandingAmount;

    @Column(name = "last_payment_date")
    private LocalDate lastPaymentDate;

    @Column(name = "repayment_due_date")
    private LocalDate repaymentDueDate;
}
