package com.msgqueue.mini_broker.exception;

import org.springframework.web.bind.annotation.*;
import com.msgqueue.mini_broker.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse(e.getMessage(), "INTERNAL_ERROR");
    }
}
