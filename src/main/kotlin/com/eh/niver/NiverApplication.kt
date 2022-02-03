package com.eh.niver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class NiverApplication

fun main(args: Array<String>) {
    runApplication<NiverApplication>(*args)
}
