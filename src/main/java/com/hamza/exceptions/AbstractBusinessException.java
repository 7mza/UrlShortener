package com.hamza.exceptions;

import com.hamza.dtos.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AbstractBusinessException extends RuntimeException {
    protected String code;
    protected String message;
    protected Instant timestamp = Instant.now();
    protected String path;
    protected HttpStatus httpStatus;

    public ErrorDto toDto() {
        return new ErrorDto(this.code, this.message, this.timestamp.toString());
    }
}
