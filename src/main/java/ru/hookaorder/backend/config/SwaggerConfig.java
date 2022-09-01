package ru.hookaorder.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        		return new Docket(DocumentationType.SWAGGER_2)
        			.select()
        			.apis(RequestHandlerSelectors.basePackage("ru.hookaorder.backend.feature.place.controller"))
        		    .paths(PathSelectors.any())
        			.build();

        	}
}
