package com.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(

    info = @Info(
        title = "User Management ",
        version = "1.0.0",

        description = """
            This REST API provides complete User Management functionality.

            Features:
            - User Registration
            - User Login with JWT Authentication
            - Role-Based Access Control (Admin/User)
            - View and Update User Profile
            - Admin User Management

            Authentication:
            1. Login using /api/auth/login
            2. Copy the JWT token from response
            3. Click 'Authorize' in Swagger
            4. Paste: Bearer <your-token>

            All secured endpoints require JWT authorization.
            """
    ),

    // Apply JWT globally
    security = @SecurityRequirement(name = "bearerAuth")
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

            // Enable JWT globally
            .addSecurityItem(
                new io.swagger.v3.oas.models.security.SecurityRequirement()
                    .addList("bearerAuth")
            )

            .components(
                new Components()

                    .addSecuritySchemes(
                        "bearerAuth",

                        new SecurityScheme()
                            .name("Authorization")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            );
    }
}
