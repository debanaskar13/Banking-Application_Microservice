package com.microservice.cards.service;

import com.microservice.cards.dto.CardsDto;

public interface ICardsService {

    /**
     *
     * @param customerId - Mobile Number of the Customer
     */
    void createCard(String customerId);

    /**
     *
     * @param customerId - Input mobile Number
     *  @return Card Details based on a given mobileNumber
     */
    CardsDto fetchCard(String customerId);

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateCard(CardsDto cardsDto);

    /**
     *
     * @param customerId - Input Mobile Number
     * @return boolean indicating if the delete of card details is successful or not
     */
    boolean deleteCard(String customerId);

}
