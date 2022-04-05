package com.searching.instagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerConfiguration() {

        Set<String> consumes = new HashSet<>();
        consumes.add(MediaType.APPLICATION_JSON_VALUE);

        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(consumes)
                .produces(consumes)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.searching.instagram"))
                .build()
                .apiInfo(apiDetails());

    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Instagram API",
                "Api docs for Backend of Instagram",
                "1.0",
                "Searching",
                new Contact("Sirojiddin",
                        "t.me/+998994246558",
                        "ssirojiddin2004@gmail.com"),
                "",
                "",
                Collections.emptyList()
        );
    }
    private ApiInfo apiDetails_2() {
        return new ApiInfo(
                "Instagram API",
                "Api docs for Backend of Instagram",
                "1.0",
                "Searching",
                new Contact("Muxriddin",
                        "t.me/+998994214924",
                        "muxriddin200228@gmail.com"),
                "",
                "",
                Collections.emptyList()
        );
    }

}
