package com.microservice.account.repository;

import com.microservice.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByAccountNumber(Long accountNumber);
    List<Account> findByCustomerId(String customerId);
    @Transactional
    @Modifying
    void deleteAllByCustomerId(String customerId);
}
