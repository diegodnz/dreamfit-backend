package com.dreamfitbackend.configs.swagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dreamfitbackend.configs.exceptions.MessageException;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.dreamfitbackend.api"))
          .paths(PathSelectors.any())
          .build()
          .useDefaultResponseMessages(false)
//        .globalResponseMessage(RequestMethod.GET, responseMessageForGET())
          .apiInfo(apiInfo())
          .securitySchemes(Arrays.asList(apiKey()));
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DreamFit API")
                .description("Spring Boot REST API")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("DreamFit", "", "DreamFitAcademiaPE@gmail.com"))
                .build();
    }
    
    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
    
//    private List<ResponseMessage> responseMessageForGET()
//    {
//        return new ArrayList<ResponseMessage>() {/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//		{
//            add(new ResponseMessageBuilder()
//                .code(401)
//                .message("401 - Unauthorized")
//                .build());
//            add(new ResponseMessageBuilder()
//                .code(403)
//                .message("403 - Forbidden")
//                .build());  
//            add(new ResponseMessageBuilder()
//                .code(500)
//                .message("500 - Internal Server Error")
//                .build());
//        }};
//    }
}
