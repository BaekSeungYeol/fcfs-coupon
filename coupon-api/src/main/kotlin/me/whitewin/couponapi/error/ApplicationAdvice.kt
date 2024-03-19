package me.whitewin.couponapi.error

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import me.whitewin.couponcore.error.ApplicationException
//import me.whitewin.couponcore.error.ApplicationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApplicationAdvice {

    @ExceptionHandler(ApplicationException::class)
    fun applicationExceptionHandler(ex: ApplicationException): ResponseEntity<ServerExceptionResponse> {
        return ResponseEntity
            .status(ex.httpStatus)
            .body(ServerExceptionResponse(ex.code, ex.reason))
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class ServerExceptionResponse(
        val code: String,
        val reason: String?
    )
}