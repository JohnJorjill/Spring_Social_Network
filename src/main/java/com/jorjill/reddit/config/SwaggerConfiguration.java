package com.jorjill.reddit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    // Docket - summary or other brief statement of contents of document
    @Bean
    public Docket redditCloneApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    // details of our api
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Social Network API")
                .version("1.0")
                .description("API for Social Network Application")
                .contact(new Contact("John Jorjill", "http://programmingtechie.com", "xyz@email.com"))
                .license("Apache License Version 2.0")
                .build();
    }
}
