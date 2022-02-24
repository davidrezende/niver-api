package com.eh.niver.controller

import com.eh.niver.model.Person
import com.eh.niver.model.vo.Credentials
import com.eh.niver.model.vo.JWTResponse
import com.eh.niver.security.util.JWTUtil
import com.eh.niver.service.AuthenticationService
import com.eh.niver.service.PersonService
import com.eh.niver.service.UserService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@Api(value = "Endpoints de autenticacao.")
@RestController
@RequestMapping("auth/api")
class AuthenticationController(val authenticationService: AuthenticationService) {
//    @Autowired
//    private lateinit var userService: UserService

    @PostMapping("/authenticate")
    fun authenticatePersonAndGenerateToken(@RequestBody authenticationRequest: Credentials): ResponseEntity<*> {
        return ResponseEntity.ok<Any>(authenticationService.generateToken(authenticationRequest))
    }

    @PostMapping
    fun savePersonWithBCryptPassword(@RequestBody person: Person): Person {
        return authenticationService.createUser(person)
    }

//    @GetMapping("/me")
//    fun me() = ResponseEntity.ok(userService.myself()!!)
}