package com.ecommerce.pricing.infrastructure.rest.advice;

import com.ecommerce.pricing.domain.exception.DomainValidationException;
import com.ecommerce.pricing.domain.exception.PriceNotFoundException;
import com.ecommerce.pricing.infrastructure.rest.api.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;

import static com.ecommerce.pricing.infrastructure.rest.advice.constant.GlobalExceptionHandlerConstant.BAD_REQUEST_ERROR;
import static com.ecommerce.pricing.infrastructure.rest.advice.constant.GlobalExceptionHandlerConstant.INTERNAL_SERVER_ERROR;
import static com.ecommerce.pricing.infrastructure.rest.advice.constant.GlobalExceptionHandlerConstant.INTERNAL_SERVER_ERROR_MSG;
import static com.ecommerce.pricing.infrastructure.rest.advice.constant.GlobalExceptionHandlerConstant.NOT_FOUND_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePriceNotFoundException(
            PriceNotFoundException ex, WebRequest request) {
        var errorResponse = new ErrorResponse()
                .status(HttpStatus.NOT_FOUND.value())
                .error(NOT_FOUND_ERROR)
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<ErrorResponse> handleDomainValidationException(
            DomainValidationException ex, WebRequest request) {
        var errorResponse = new ErrorResponse()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(BAD_REQUEST_ERROR)
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        var errorResponse = new ErrorResponse()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(INTERNAL_SERVER_ERROR)
                .message(INTERNAL_SERVER_ERROR_MSG)
                .timestamp(OffsetDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
