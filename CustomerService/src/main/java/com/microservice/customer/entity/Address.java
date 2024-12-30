package com.microservice.customer.entity;

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
@Document(collection = "Addresses")
public class Address extends BaseEntity {

    @Id
    private String id;
    private String city;
    private String state;
    private String zip;
    private String country;

}
