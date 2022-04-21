package com.hamza.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamza.dtos.ErrorDto;
import com.hamza.exceptions.AbstractBusinessException;
import com.hamza.exceptions.MalformedUrlException;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AbstractBusinessException.class)
    public ResponseEntity<Object> handleBusinessExceptions(AbstractBusinessException ex, WebRequest request) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        return handleExceptionInternal(ex, new ObjectMapper().writeValueAsString(ex.toDto()), headers, ex.getHttpStatus(), request);
    }

    @SneakyThrows
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        AbstractBusinessException e = new MalformedUrlException();
        ErrorDto errorDto = new ErrorDto(e.getCode(), e.getMessage(), e.getTimestamp().toString());
        return handleExceptionInternal(ex, new ObjectMapper().writeValueAsString(errorDto), headers, status, request);
    }

    @SneakyThrows
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        AbstractBusinessException e = new MalformedUrlException();
        ErrorDto errorDto = new ErrorDto(e.getCode(), e.getMessage(), e.getTimestamp().toString());
        return handleExceptionInternal(ex, new ObjectMapper().writeValueAsString(errorDto), headers, status, request);
    }
}
