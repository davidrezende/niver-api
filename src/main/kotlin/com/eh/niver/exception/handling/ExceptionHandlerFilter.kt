package com.eh.niver.exception.handling

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionHandlerFilter : OncePerRequestFilter() {

    private fun createErrorBody(exception: Exception ) : String {
        val errorDetails = ErrorsDetails(
            Date(),
            HttpStatus.UNAUTHORIZED.reasonPhrase,
            exception.message!!
        )
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(errorDetails)
    }

    data class ErrorsDetails(val time: Date, val message: String, val details: String)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: RuntimeException) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            response.contentType = "application/json;charset=UTF-8"
            response.writer.write(createErrorBody(e))
        }
    }


}