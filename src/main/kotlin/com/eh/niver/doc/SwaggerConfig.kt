package com.eh.niver.doc

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun internalApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("internal-apis")
            .pathsToMatch("/**")
            .pathsToExclude("/auth/**")
            .build()
    }

    @Bean
    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("public-apis")
            .pathsToMatch("/invite/api/info/**", "/auth/api/**")
            .build()
    }

    @Bean
    fun authApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("auth-api")
            .pathsToMatch("/auth/**")
            .build()
    }


    @Bean
    fun springShopOpenAPI(): OpenAPI? {
        return OpenAPI()
            .info(
                Info().title("Niver API")
                    .description("API para gerenciar e enviar notificacoes para aniversariantes")
                    .version("v1.0.0")
                    .license(License().name("Apache 2.0").url("http://springdoc.org"))
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("Niver Api Docs")
                    .url("https://github.com/davidrezende/niver-api")
            )
    }
}