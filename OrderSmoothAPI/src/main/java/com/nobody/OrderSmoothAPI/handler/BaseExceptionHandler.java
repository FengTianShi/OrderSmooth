package com.nobody.OrderSmoothAPI.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException e) {

        FieldError fieldError = e.getBindingResult().getFieldError();

        if (fieldError != null)
            return ResponseEntity
                    .badRequest()
                    .body(fieldError.getDefaultMessage());
        else
            return ResponseEntity
                    .badRequest()
                    .body(null);
    }
}
