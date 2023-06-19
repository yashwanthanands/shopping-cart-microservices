package com.yash.dev.exception;

import lombok.Data;

/**
 * @author yashwanthanands
 */

@Data
public class ProductServiceCustomException extends RuntimeException {

    private String errorCode;

    public ProductServiceCustomException(String message,String errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
}
