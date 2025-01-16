package com.microservice.transaction.annotation;


import com.microservice.transaction.dto.ErrorResponseDto;
import com.microservice.transaction.dto.ResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK",
                content = @Content(
                    schema = @Schema(implementation = ResponseDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "417",
                description = "HTTP Status Expectation Failed",
                content = @Content(
                        schema = @Schema(implementation = ResponseDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
        )
})
public @interface ApiResponse_200_417_500 {
}
