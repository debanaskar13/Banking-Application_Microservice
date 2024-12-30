package com.microservice.cards.repository;

import com.microservice.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {

    Optional<Cards> findByCustomerId(String customerId);

    Optional<Cards> findByCardNumber(String cardNumber);

}
