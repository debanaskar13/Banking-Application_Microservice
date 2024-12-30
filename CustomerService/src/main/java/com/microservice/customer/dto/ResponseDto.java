package com.microservice.customer.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    name = "Response",
    description = "Schema to hold response information"
)
public class ResponseDto {

    @Schema(
        description = "HTTP Status Code"
    )
    private String statusCode;
    @Schema(
        description = "HTTP Status Message"
    )
    private String statusMessage;

}
