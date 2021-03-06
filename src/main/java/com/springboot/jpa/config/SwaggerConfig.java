package com.springboot.jpa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version:not found!}") String appVersion) {
        
		Components components = new Components().addSecuritySchemes(
														"bearer-key"
													,	new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
												);

		Info info = new Info().title("Spring Boot JPA")
								.version(appVersion)
								.description("Spring Boot JPA 입니다.");

		return new OpenAPI().components(components)
							.info(info);
    }
}