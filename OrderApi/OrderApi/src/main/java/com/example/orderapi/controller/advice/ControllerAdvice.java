package com.example.orderapi.controller.advice;

import com.example.orderapi.exception.GeneralErrorException;
import com.example.orderapi.exception.InvalidRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Log4j2
public class ControllerAdvice {
    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException e, WebRequest request) {
        log.error("handleInvalidRequestException: {}", e.getMessage());
        return new ResponseEntity<>("Invalid request",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {GeneralErrorException.class})
    public ResponseEntity<String> handleGeneralErrorException(GeneralErrorException e, WebRequest request) {
        log.error("handleGeneralErrorException: {}", e.getMessage());
        return new ResponseEntity<>("Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> handleUnknownException(Exception e, WebRequest request) {
        log.error("handleUnknownException: {}", e.getMessage());
        return new ResponseEntity<>("Unknown",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
