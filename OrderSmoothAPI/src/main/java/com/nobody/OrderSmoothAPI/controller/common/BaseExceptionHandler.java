package com.nobody.OrderSmoothAPI.controller.common;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nobody.OrderSmoothAPI.dto.common.HttpResponse;

@ControllerAdvice
public class BaseExceptionHandler {
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpResponse handle(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();

        if (fieldError != null)
            return HttpResponse.builder()
                    .code(500)
                    .msg(fieldError.getDefaultMessage())
                    .build();
        else
            return HttpResponse.builder()
                    .code(500)
                    .build();
    }
}
