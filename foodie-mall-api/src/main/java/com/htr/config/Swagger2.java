package com.htr.config;

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

/**
 * @Author: T. He
 * @Date: 2021/4/28
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

// http://localhost:8088/swagger-ui.html 原路径
// http://localhost:8088/doc.html 原路径


    //配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.htr.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Foodie-mall e-commerce platform API")
                .contact(new Contact("htr",
                        "www.xyhelado.tk",
                        "hhe6389@gmail.com"))
                .description("API docoment for foodie-mall website")
                .version("1.0.1")
                .termsOfServiceUrl("www.xyhelado.tk")
                .build();
    }
}
