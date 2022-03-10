package com.eh.niver.controller

import com.eh.niver.model.Person
import com.eh.niver.model.vo.Credentials
import com.eh.niver.service.AuthenticationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Autenticacao")
@RestController
@RequestMapping("auth/api")
class AuthenticationController(val authenticationService: AuthenticationService) {
//    @Autowired
//    private lateinit var userService: UserService
    @Operation(summary = "Endpoint de login")
    @PostMapping("/authenticate")
    fun authenticatePersonAndGenerateToken(@RequestBody authenticationRequest: Credentials): ResponseEntity<*> {
        return ResponseEntity.ok<Any>(authenticationService.generateToken(authenticationRequest))
    }

    @Operation(summary = "Endpoint de registro")
    @PostMapping
    fun savePersonWithBCryptPassword(@RequestBody person: Person): Person {
        return authenticationService.createUser(person)
    }

//    @GetMapping("/me")
//    fun me() = ResponseEntity.ok(userService.myself()!!)
}