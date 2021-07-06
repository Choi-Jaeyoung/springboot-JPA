package com.springboot.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		System.out.println("=================== addCorsMappings ====================");
		registry.addMapping("/**")
				.allowedOriginPatterns("*")
				.allowedMethods("GET","POST","PUT","DELETE")
				.allowCredentials(true)
				.maxAge(3600);
	}

}
