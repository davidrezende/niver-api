package com.eh.niver

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@SecurityScheme(name = "niverapi", scheme = "Bearer", type = SecuritySchemeType.HTTP, `in` = SecuritySchemeIn.HEADER)
@EnableScheduling
class NiverApplication

fun main(args: Array<String>) {
    runApplication<NiverApplication>(*args)
}
