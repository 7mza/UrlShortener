package com.hamza.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hamza"))
                .paths(PathSelectors.regex("/api/short_url/.*"))
                .build()
                .apiInfo(
                        new ApiInfo(
                                "URL Shortener",
                                "CRUD operations for a simple URL Shortener",
                                "0.0.1",
                                null,
                                new Contact("Hamza BAAZIZ", null, "baazizh@gmail.com"),
                                "",
                                "",
                                Collections.emptyList()
                        )
                )
                .globalResponses(HttpMethod.GET, Collections.emptyList())
                .globalResponses(HttpMethod.POST, Collections.emptyList())
                .globalResponses(HttpMethod.PUT, Collections.emptyList())
                .globalResponses(HttpMethod.DELETE, Collections.emptyList());
    }
}
