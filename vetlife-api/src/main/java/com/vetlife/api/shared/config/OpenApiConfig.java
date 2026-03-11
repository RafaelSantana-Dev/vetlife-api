package com.vetlife.api.shared.config;

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
                        .title("VetLife+ API")
                        .description("API REST para gestão de clínicas veterinárias - Módulos: Cliente, Pets e Agendamentos.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Rafael Santana")
                                .url("https://github.com/RafaelSantana-Dev")));
    }
}