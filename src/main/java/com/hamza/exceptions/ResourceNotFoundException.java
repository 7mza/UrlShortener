package com.hamza.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFoundException extends AbstractBusinessException {
    public ResourceNotFoundException() {
        this.code = "002";
        this.message = "Resource not found";
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
