package com.wist.tensquare.gathering.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("gathering")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wist.tensquare.gathering.web.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("wist", "www.wist.com", "aus4756@dingtalk.com");
        return new ApiInfo(
                "活动模块接口Api",
                "Api Documentation",
                "v1.0",
                "localhost:9004/gathering",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
