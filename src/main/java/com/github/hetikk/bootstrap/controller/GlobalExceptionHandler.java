package com.github.hetikk.bootstrap.controller;

import com.github.hetikk.bootstrap.common.exception.DataAccessException;
import com.github.hetikk.bootstrap.common.model.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetails common(Throwable ex) {
        log.error("Unexpected server exception.", ex);
        String message = ExceptionUtils.getRootCauseMessage(ex);
        return ErrorDetails.from(message);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetails dataAccess(Throwable ex) {
        log.error("Data access exception.", ex);
        String message = ExceptionUtils.getRootCauseMessage(ex);
        return ErrorDetails.from(message);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        log.error("No handler found exception.", ex);
        String message = ExceptionUtils.getRootCauseMessage(ex);
        return new ResponseEntity<>(ErrorDetails.from(message), status);
    }

}
