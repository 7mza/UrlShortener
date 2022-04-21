package com.hamza.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnhandledException extends AbstractBusinessException {
    public UnhandledException(String message) {
        this.code = "001";
        this.message = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
