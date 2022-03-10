package com.eh.niver.exception.custom

import org.springframework.security.core.AuthenticationException

class InvalidTokenException(message: String?) : AuthenticationException(message) {
}