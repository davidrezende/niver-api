package com.eh.niver.security

import com.eh.niver.exception.custom.InvalidTokenException
import com.eh.niver.security.util.JWTUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private var jwtUtil: JWTUtil,
    private var userDetailService: UserDetailsService
) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
            val authorizationHeader = request.getHeader("Authorization")
            if (authorizationHeader.startsWith("Bearer")) {
                val auth = getAuthentication(authorizationHeader)
                SecurityContextHolder.getContext().authentication = auth
            }else{
                throw InvalidTokenException("There is no AccessToken in a request header");
            }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(authorizationHeader: String?): UsernamePasswordAuthenticationToken {
        val token = authorizationHeader?.substring(7) ?: ""
        if (jwtUtil.isTokenValid(token)) {
            val username = jwtUtil.getUserName(token)
            val user = userDetailService.loadUserByUsername(username)
            return UsernamePasswordAuthenticationToken(user, null, user.authorities)
        }
        throw InvalidTokenException("Auth invalid")
    }

}