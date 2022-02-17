package com.eh.niver.security.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*


@Component
class JWTUtil {

    @Value("\${jwt.secret}")
    lateinit var secret: String

    @Value("\${jwt.expiration}")
    lateinit var expiration: String

    companion object {
        private val logger = LoggerFactory.getLogger(JWTUtil::class.java)
    }

    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuer("http://issuer.com.br")
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration.toLong()))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

    fun isTokenValid(token: String): Boolean {
        logger.info( "token: $token" )
        val claims = getClaimsToken(token)
        logger.info( "claims: ${claims.toString()}" )
        if (claims != null) {
            val username = claims.subject
            val expirationDate = claims.expiration
            val now = Date(System.currentTimeMillis())
            logger.info( "data de validada valida: ${now.before(expirationDate)}" )
            if (username != null && expirationDate != null && now.before(expirationDate)) {
                return true
            }
        }
        return false
    }

    private fun getClaimsToken(token: String): Claims? {
        return try {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body
        } catch (e: Exception) {
            null
        }
    }

    fun getUserName(token: String): String? {
        val claims = getClaimsToken(token)
        return claims?.subject
    }


}