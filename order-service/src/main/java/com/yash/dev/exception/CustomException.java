package com.yash.dev.exception;

import lombok.Data;

/**
 * @author yashwanthanands
 */

@Data
public class CustomException extends RuntimeException {
    private String errorCode;
    private int status;

    public CustomException(String message,String errorCode, int status) {
        super(message);
        this.errorCode=errorCode;
        this.status=status;
    }
}
