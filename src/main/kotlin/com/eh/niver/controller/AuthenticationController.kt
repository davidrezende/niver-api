package com.eh.niver.controller

import com.eh.niver.model.Person
import com.eh.niver.model.vo.Credentials
import com.eh.niver.model.vo.JWTResponse
import com.eh.niver.security.util.JWTUtil
import com.eh.niver.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("auth/api")
class AuthenticationController {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtTokenUtil: JWTUtil

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: Credentials): ResponseEntity<*> {
        println(authenticationRequest.toString())
        authenticate(authenticationRequest.email, authenticationRequest.password)
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(authenticationRequest.email)
        val token: String = jwtTokenUtil.generateToken(userDetails.username)
        return ResponseEntity.ok<Any>(JWTResponse(token))
    }

    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        }
    }

    @PostMapping
    fun savePerson(@RequestBody person: Person): Person {
        return userService.create(person)
    }

    @GetMapping("/me")
    fun me() = ResponseEntity.ok(userService.myself()!!)
}