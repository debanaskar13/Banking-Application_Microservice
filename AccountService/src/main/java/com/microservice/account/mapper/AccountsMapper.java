package com.microservice.account.mapper;

import com.microservice.account.dto.AccountDto;
import com.microservice.account.entity.Account;

public class AccountsMapper {

    public static AccountDto mapToAccountsDto(Account account, AccountDto accountDto) {
        accountDto.setAccountNumber(String.valueOf(account.getAccountNumber()));
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranch(account.getBranch());
        accountDto.setStatus(account.getStatus());
        return accountDto;
    }

    public static Account mapToAccount(AccountDto accountDto, Account account, String action) {
        if(action.equals("insert")){
            account.setAccountNumber(Long.parseLong(accountDto.getAccountNumber()));
        }
        account.setAccountType(accountDto.getAccountType());
        account.setBranch(accountDto.getBranch());
        return account;
    }
}
