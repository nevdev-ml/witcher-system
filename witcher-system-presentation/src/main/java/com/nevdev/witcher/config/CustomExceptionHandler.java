package com.nevdev.witcher.config;

import com.nevdev.witcher.models.ErrorResponse;
import com.nevdev.witcher.services.exceptions.RequestException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {
    public CustomExceptionHandler() {
    }

    @ExceptionHandler({RequestException.class})
    @ResponseBody
    public ErrorResponse badRequestException(RequestException exception) {
        return new ErrorResponse("BAD_REQUEST", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    public ErrorResponse defaultHandler(Exception ex) {
        return new ErrorResponse("INTERNAL_SERVER", ex.getMessage());
    }
}
