package com.students.studentsApp.configurations;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
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
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.students.studentsApp.controllers"))
                .paths(paths())
                .build()
                .apiInfo(metaData());
    }

    private Predicate<String> paths(){
        return Predicates.or(PathSelectors.regex("/api/students.*"));
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Students API Documentation")
                .description("Esto es la documentación para Students API")
                .version("1.0")
                .license("")
                .licenseUrl("")
                .contact(new Contact("Lincol Morales Roca", "https://www.facebook.com/lincolalejandro.moralesroca/", "lincol.morales.25@gmail.com"))
                .build();
    }

    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
