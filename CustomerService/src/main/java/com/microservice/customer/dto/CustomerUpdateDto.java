package com.microservice.customer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerUpdateDto {

    @Size(min = 3, message = "First name must be at least 3 characters long")
    @Size(max = 50, message = "First name must be at most 50 characters long")
    private String firstName;

    @Size(min = 3, message = "Last name must be at least 3 characters long")
    @Size(max = 50, message = "Last name must be at most 50 characters long")
    private String lastName;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    private String phone;

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d{2})$", message = "Invalid date format. Expected dd/MM/yyyy and must be after 1900 and before current year")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String dateOfBirth;

    @Pattern(regexp = "MALE|FEMALE|OTHERS", message = "Invalid gender value")
    private String gender;
}
