package kr.bistroad.authservice

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors.any
import springfox.documentation.builders.RequestHandlerSelectors.basePackage
import springfox.documentation.service.Tag
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {
    @Bean
    fun swaggerDocket() = Docket(DocumentationType.OAS_30)
        .apiInfo(
            ApiInfoBuilder().title("Auth API").build()
        )
        .tags(Tag("/auth", "Auth API"))
        .select()
        .apis(basePackage("org.springframework.boot").negate())
        .paths(any())
        .build()!!
}