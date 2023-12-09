package com.jwt.example.config;



import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		servers = {
				@Server(
				description = "Local ENV",
				url="http://projectms.cv8a5x4dfyqp.us-east-2.rds.amazonaws.com:3307:8080/"
				)
		},
		security= {
				@SecurityRequirement(name="bearerAuth")
		}
		
		)

@SecurityScheme(
		name="bearerAuth",
		
		description="JWT auth description",
		
		scheme = "bearer", 
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
		)
public class OpenApiConfig {

}
