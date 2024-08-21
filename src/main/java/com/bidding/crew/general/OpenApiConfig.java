package com.bidding.crew.general;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "PGromiec",
                        email = "pgromiec@gmail.com"),
                description = "OpenApi documentation for Spring application",
                title = "OpenApi spec - PGromiec",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
