package com.hamza.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MalformedUrlException extends AbstractBusinessException {
    public MalformedUrlException() {
        this.code = "003";
        this.message = "Malformed URL";
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
