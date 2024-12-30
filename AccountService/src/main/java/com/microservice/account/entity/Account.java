package com.microservice.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {

    @Column(name = "account_number",unique = true,nullable = false)
    @Id
    private Long accountNumber;
    @Column(name = "customer_id",nullable = false)
    private String customerId;
    @Column(name = "account_type",nullable = false)
    private String accountType;
    private String branch;
    private Double balance;
    private String status;
    private String currency;
}
