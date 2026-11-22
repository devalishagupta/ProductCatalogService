package com.myprojects.productcatalogservice.controllers;

import com.myprojects.productcatalogservice.exceptions.ThirdPartyApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ThirdPartyApiException.class)
    public ResponseEntity<String> handleThirdPartyException(ThirdPartyApiException ex){
        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(ex.getMessage());
    }
}
