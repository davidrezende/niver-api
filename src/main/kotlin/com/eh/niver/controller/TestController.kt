package com.eh.niver.controller

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api("Controller de Teste")
@RestController
@RequestMapping("/test/api")
class TestController {

    @GetMapping("/testing")
    fun testing() : String{
        return "OK"
    }
}