package com.organization.imagesearch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final Contact DEFAULT_CONTACT = new Contact("Priyanka", "organization.com", "pchaurishia@organization.com");
    private static final ApiInfo DEFAULT_API_INFO   = new ApiInfo("Image Service Documentation", "Image Service Documentation", "1.0", "urn:tos",DEFAULT_CONTACT, "ABC 1.0", "http://www.google.com", new ArrayList());
    private static final Set<String> DEFAULT_PRODUCE_AND_CONSUMES = new HashSet<>(Arrays.asList("applcation/json", "application/xml"));

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCE_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCE_AND_CONSUMES);
    }
}
