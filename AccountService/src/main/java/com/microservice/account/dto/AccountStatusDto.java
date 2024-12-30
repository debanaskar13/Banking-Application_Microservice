package com.microservice.account.dto;

import com.microservice.account.constants.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatusDto {

    private Long accountNumber;
    private AccountStatus status;
}
