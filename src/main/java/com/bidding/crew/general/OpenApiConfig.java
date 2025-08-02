package com.bidding.crew.general;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "PGromiec",
                        email = "dev.gromiec@gmail.com"),
                description = "OpenApi documentation for Spring application",
                title = "Crew Bidding Application - PGromiec",
                version = "1.0"
        )
)
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addTagsItem(new Tag().name("1. Authentication").description("Handles registration and login"))
                .addTagsItem(new Tag().name("2. User Management").description("Retrieving and filtering users"))
                .addTagsItem(new Tag().name("3. Events").description("Querying custom events"))
                .addTagsItem(new Tag().name("4. Flights").description("Managing flights"))
                .addTagsItem(new Tag().name("5. Report").description("Final report operations"))
                .addTagsItem(new Tag().name("6. Admin").description("Role management"))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
    }
}
