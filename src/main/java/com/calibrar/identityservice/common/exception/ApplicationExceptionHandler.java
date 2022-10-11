package com.calibrar.identityservice.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        final ErrorResponse apiException = new ErrorResponse(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> conflictConstraintHandler(DataIntegrityViolationException e) {
        final ErrorResponse apiException = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> entityNotFoundHandler(EntityNotFoundException e) {
        final ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> badInputHandler(HttpMessageNotReadableException httpError) {
        final ErrorResponse response = new ErrorResponse(
                httpError.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
