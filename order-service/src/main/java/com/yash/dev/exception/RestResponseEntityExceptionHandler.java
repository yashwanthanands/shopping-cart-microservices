package com.yash.dev.exception;

import com.yash.dev.external.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author yashwanthanands
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleOrderServiceException(CustomException customException) {
        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMessage(customException.getMessage())
                .errorCode(customException.getErrorCode())
                .build(), HttpStatus.NOT_FOUND);
    }
}
