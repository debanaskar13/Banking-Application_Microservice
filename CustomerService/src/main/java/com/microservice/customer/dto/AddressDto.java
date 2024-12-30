package com.microservice.customer.dto;

import com.microservice.customer.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Address", description = "Schema to hold Address details of a customer")
public class AddressDto {

    @Schema(
            description = "Unique identifier for the address",
            example = "123e4567-e89b-12d3-a456-426655440000"
    )
    private String id;
    @Schema(
            description = "City of the address",
            example = "Kolkata"
    )
    private String city;
    @Schema(
            description = "State of the address",
            example = "West Bengal"
    )
    private String state;
    @Schema(
            description = "Zip code of the address",
            example = "700001"
    )
    private String zip;
    @Schema(
            description = "Country of the address",
            example = "India"
    )
    private String country;

}
