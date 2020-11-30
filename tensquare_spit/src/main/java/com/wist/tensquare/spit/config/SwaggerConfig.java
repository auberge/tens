package com.wist.tensquare.spit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.print.Doc;
import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("spit")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wist.tensquare.spit.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("wist", "www.wist.com", "aus4756@dingtalk.com");
        return new ApiInfo(
                "吐槽模块接口Api",
                "Api Documentation",
                "v1.0",
                "localhost:9003/recruit",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
