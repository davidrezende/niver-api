package com.eh.niver.controller

import com.eh.niver.model.Person
import com.eh.niver.service.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user/api")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @ApiOperation(value = "Salva uma pessoa.")
    @PostMapping
    fun savePerson(@RequestBody person: Person): Person {
        return userService.create(person)
    }

    @GetMapping("/me")
    fun me() = ResponseEntity.ok(userService.myself()!!)
}