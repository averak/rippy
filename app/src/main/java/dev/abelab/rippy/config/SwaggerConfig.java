package dev.abelab.rippy.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import dev.abelab.rippy.property.RippyProperty;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    RippyProperty rippyProperty;

    @Bean
    public Docket dock() {
        return new Docket(DocumentationType.SWAGGER_2) //
            .useDefaultResponseMessages(false) //
            .protocols(Collections.singleton(this.rippyProperty.getProtocol())) //
            .host(this.rippyProperty.getHostname()) //
            .select() //
            .apis(RequestHandlerSelectors.basePackage("dev.abelab.rippy.api.controller.internal")) //
            .build() //
            .apiInfo(apiInfo()) //
            .tags( //
                new Tag("Auth", "認証"), //
                new Tag("User", "ユーザ"), //
                new Tag("Event", "イベント") //
            );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder() //
            .title("Rippy Internal API") //
            .version("1.0") //
            .build();
    }
}
