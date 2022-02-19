package com.eh.niver.security

import com.eh.niver.exception.custom.InvalidTokenException
import com.eh.niver.model.vo.Credentials
import com.eh.niver.model.vo.UserDetailsImpl
import com.eh.niver.security.util.JWTUtil
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthenticationFilter(authenticationManager: AuthenticationManager, private var jwtUtil: JWTUtil) :
    UsernamePasswordAuthenticationFilter() {


    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication? {
        try {
            val (email, password) = ObjectMapper().readValue(request.inputStream, Credentials::class.java)

            val token = UsernamePasswordAuthenticationToken(email, password)
            token.details = email

            return authenticationManager.authenticate(token)
        } catch (e: Exception) {
            throw UsernameNotFoundException("User not found!")
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        if (authorizationHeader.startsWith("Bearer")) {
            val username = (authResult.principal as UserDetailsImpl).username
            val token = jwtUtil.generateToken(username)
            response.addHeader("Authorization", "Bearer $token")
        } else {
            throw InvalidTokenException("There is no AccessToken in a request header");
        }
    }

}