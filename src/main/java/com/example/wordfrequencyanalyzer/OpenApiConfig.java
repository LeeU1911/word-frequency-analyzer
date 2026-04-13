package com.example.wordfrequencyanalyzer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Word Frequency Analyzer API")
                        .version("1.0.0")
                        .description("REST API for analyzing word frequency in text")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@example.com")));
    }
}
