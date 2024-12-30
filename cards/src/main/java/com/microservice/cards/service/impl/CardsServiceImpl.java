package com.microservice.cards.service.impl;

import com.microservice.cards.constants.CardsConstants;
import com.microservice.cards.dto.CardsDto;
import com.microservice.cards.dto.ResponseDto;
import com.microservice.cards.entity.Cards;
import com.microservice.cards.exception.CardAlreadyExistsException;
import com.microservice.cards.exception.ResourceNotFoundException;
import com.microservice.cards.external.CustomerService;
import com.microservice.cards.mapper.CardsMapper;
import com.microservice.cards.repository.CardsRepository;
import com.microservice.cards.service.ICardsService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardsServiceImpl implements ICardsService {

    private final CardsRepository cardsRepository;
    private final CustomerService customerService;

    /**
     * @param customerId - Customer Id of the Customer
     */
    @Override
    public void createCard(String customerId) {

        try{
            ResponseDto responseDto = customerService.isCustomerExist(customerId);

            Optional<Cards> optionalCards= cardsRepository.findByCustomerId(customerId);
            if(optionalCards.isPresent()){
                throw new CardAlreadyExistsException("Card already registered with given customerId : "+customerId);
            }
            cardsRepository.save(createNewCard(customerId));
        }catch (FeignException.NotFound e) {
            log.error("Customer not found {}", e.getMessage());
            throw new ResourceNotFoundException("Customer", "customer id", customerId);
        }catch (Exception e){
            log.error("Error creating card {}", e.getMessage());
            throw new RuntimeException("Error creating card");
        }
    }

    /**
     * @param customerId - Customer Id of the Customer
     * @return the new card details
     */
    private Cards createNewCard(String customerId) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setCustomerId(customerId);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    /**
     *
     * @param customerId - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardsDto fetchCard(String customerId) {
        Cards cards = cardsRepository.findByCustomerId(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", customerId)
        );
        return CardsMapper.mapToCardsDto(cards, new CardsDto());
    }

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return  true;
    }

    /**
     * @param customerId - Input Customer Id
     * @return boolean indicating if the delete of card details is successful or not
     */
    @Override
    public boolean deleteCard(String customerId) {
        Cards cards = cardsRepository.findByCustomerId(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", customerId)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }


}
