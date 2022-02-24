package com.eh.niver.service.impl

import com.eh.niver.model.Person
import com.eh.niver.model.vo.Credentials
import com.eh.niver.model.vo.JWTResponse
import com.eh.niver.security.util.JWTUtil
import com.eh.niver.service.AuthenticationService
import com.eh.niver.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl: AuthenticationService {

    @Autowired
    lateinit var personService: PersonService

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @Autowired
    lateinit var jwtTokenUtil: JWTUtil

    override fun authenticate(email: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        }
    }

    override fun generateToken(request: Credentials): JWTResponse {
        authenticate(request.email, request.password)
        val personData = personService.getPersonByEmail(request.email).get()
        val token: String = jwtTokenUtil.generateToken(personData.email)
        return JWTResponse(token, personData.idPerson!!, personData.name)
    }

    override fun generateBCryptPassword(password: String) : String{
        return bCryptPasswordEncoder.encode(password)
    }

    override fun createUser(person: Person) : Person{
        person.password = generateBCryptPassword(person.password)
        return personService.savePerson(person)
    }
}