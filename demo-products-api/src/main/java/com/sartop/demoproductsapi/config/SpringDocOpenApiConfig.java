package com.sartop.demoproductsapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig
{
    @Bean
    OpenAPI openAPI()
    {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("REST API - Spring Products")
                                .description("API to manage products and see reports")
                                .version("v1")
                                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                                .contact(new Contact().name("Matheus Sarto").email("matheusmsarto@gmail.com"))
                );

    }
}
