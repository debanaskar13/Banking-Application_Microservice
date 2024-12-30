package com.microservice.customer.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Customer Microservice REST API Documentation",
                version = "1.0",
                description = "Customer Service",
                contact = @Contact(
                        name = "Debashis Naskar",
                        email = "codewithdeba@gmail.com",
                        url = "https://github.com/debanaskar13"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Documentation",
                url = "https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/"
        )
)
public class AppConfig {
}
