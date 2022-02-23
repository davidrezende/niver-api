package com.eh.niver.service

import com.eh.niver.model.Person
import com.eh.niver.model.vo.Credentials
import com.eh.niver.model.vo.JWTResponse
import org.springframework.http.ResponseEntity

interface AuthenticationService {
    fun authenticate(email: String, password: String)
    fun generateToken(request: Credentials): JWTResponse
    fun generateBCryptPassword(password: String) : String
    fun createUser(person: Person): Person
}