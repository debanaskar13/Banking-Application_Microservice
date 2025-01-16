package com.microservice.cards.controller;

import com.microservice.cards.annotation.ApiResponse_200_417_500;
import com.microservice.cards.annotation.ApiResponse_200_500;
import com.microservice.cards.annotation.ApiResponse_201_500;
import com.microservice.cards.constants.CardsConstants;
import com.microservice.cards.dto.CardsContactInfo;
import com.microservice.cards.dto.CardsDto;
import com.microservice.cards.dto.ResponseDto;
import com.microservice.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Debashis Naskar
 */

@Tag(
        name = "CRUD REST APIs for Cards in Banking Application",
        description = "CRUD REST APIs in Banking Application to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = "/api/cards", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

    private final ICardsService iCardsService;
    private final CardsContactInfo cardsContactInfo;

    public CardsController(ICardsService iCardsService, CardsContactInfo cardsContactInfo) {
        this.cardsContactInfo = cardsContactInfo;
        this.iCardsService = iCardsService;

    }

    @Value("${build.version}")
    private String buildVersion;


    /**
     * The API endpoint to retrieve the build version of the application.
     * <p>
     * This endpoint is intended for monitoring and debugging purposes.
     * <p>
     * The build version is returned as a plain text string.
     * */

    @Operation(
            summary = "Get Build Information",
            description = "Get Build Information that is deployed into account microservice"
    )
    @ApiResponse_200_500
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    /**
     * Get the contact information of the support team.
     *
     * @return the contact information of the support team
     */
    @Operation(
            summary = "Get Contact Information",
            description = "Get Contact Information that can be reached out if any issues with account microservice"
    )
    @ApiResponse_200_500
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfo> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(cardsContactInfo);
    }



    /**
     * This API endpoint is used to create a new card based on a customer Id.
     * The API endpoint is called by the mobile application to create a new card.
     * The API endpoint should validate the input parameters and create a new card
     * in the database.
     * The API endpoint should return the HTTP status CREATED.
     * The API endpoint should return a JSON response with the HTTP status code.
     * @param customerId the customer Id of the customer
     * @return a JSON response with the HTTP status code
     */
    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card inside Banking Application"
    )
    @ApiResponse_201_500
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam String customerId) {
        iCardsService.createCard(customerId);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    /**
     * This API endpoint is used to fetch card details based on a customer Id.
     * The API endpoint is called by the mobile application to fetch card details.
     * The API endpoint should validate the input parameters and return the card
     * details from the database.
     * The API endpoint should return the HTTP status OK.
     * The API endpoint should return a JSON response with the card details.
     * @param customerId the customer Id of the customer
     * @return a JSON response with the card details
     */
    @Operation(
            summary = "Fetch Card Details REST API",
            description = "REST API to fetch card details based on a Customer Id"
    )
    @ApiResponse_200_500
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String customerId) {
        CardsDto cardsDto = iCardsService.fetchCard(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    /**
     * This API endpoint is used to update card details based on a card number.
     * The API endpoint is called by the mobile application to update card details.
     * The API endpoint should validate the input parameters and update the card
     * details in the database.
     * The API endpoint should return the HTTP status OK.
     * The API endpoint should return a JSON response with the HTTP status code.
     * @param cardsDto the card details to be updated
     * @return a JSON response with the HTTP status code
     */
    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
    )
    @ApiResponse_200_417_500
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        boolean isUpdated = iCardsService.updateCard(cardsDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    /**
     * This API endpoint is used to delete card details based on a customer Id.
     * The API endpoint is called by the mobile application to delete card details.
     * The API endpoint should validate the input parameters and delete the card
     * details from the database.
     * The API endpoint should return the HTTP status OK.
     * The API endpoint should return a JSON response with the HTTP status code.
     * @param customerId the customer Id of the customer
     * @return a JSON response with the HTTP status code
     */
    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to delete Card details based on a Customer Id"
    )
    @ApiResponse_200_417_500
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam String customerId) {
        boolean isDeleted = iCardsService.deleteCard(customerId);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }

}
