package com.cjc.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Books Management API",
                version = "1.0",
                description =
                        "### API Contact Information\n" +
                        "**Author Name:** Rajkumari  \n" +
                        "**Email ID:** rajkumari.gavande@gmail.com",
                contact = @Contact(
                        name = "Rajkumari",
                        email = "rajkumari.gavande@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
