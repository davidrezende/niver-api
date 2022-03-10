package com.eh.niver.exception.handling

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import java.util.*
import javax.persistence.EntityNotFoundException

@ControllerAdvice(annotations = [RestController::class])
class ExceptionHandlingController {

    @ExceptionHandler(
        value = [EntityNotFoundException::class,
            EmptyResultDataAccessException::class
        ]
    )
    fun handleEntityNotFound(ex: Exception, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(
            Date(),
            "Data not found",
            ex.message!!
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(value = [(HttpMessageNotReadableException::class)])
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(
            Date(),
            "Invalid parameters",
            ex.message!!
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(BadCredentialsException::class)])
    fun handleBadCredentialsException(ex: BadCredentialsException, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(
            Date(),
            HttpStatus.UNAUTHORIZED.reasonPhrase,
            ex.message!!
        )
        return ResponseEntity(errorDetails, HttpStatus.UNAUTHORIZED)
    }

}

data class ErrorsDetails(val time: Date, val message: String, val details: String)