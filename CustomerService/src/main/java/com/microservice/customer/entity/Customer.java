package com.microservice.customer.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Customers")
public class Customer extends BaseEntity{

    @Id
    private String id;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String phone;

    @DBRef
    private List<Address> address = new ArrayList<>();

    @Field(name = "date_of_birth")
    private String dateOfBirth;

    private String gender;

    public void addAddress(Address address) {
        this.address.add(address);
    }

}
