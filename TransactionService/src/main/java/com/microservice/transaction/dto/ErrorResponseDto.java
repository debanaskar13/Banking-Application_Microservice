package com.microservice.transaction.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold Error Response information"
)
public class ErrorResponseDto {

    @Schema(
            description = "API Path invoked by the client", example = "/api/v1/customers"
    )
    private String apiPath;
    @Schema(
            description = "Error code representing the error happened",example = "500"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the error happened",example = "Internal Server Error"
    )
    private String errorMessage;

    @Schema(
            description = "Time representing when the error happened"
    )
    private LocalDateTime errorTime;

}
